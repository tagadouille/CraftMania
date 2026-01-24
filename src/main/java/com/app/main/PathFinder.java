package com.app.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile.TileType;

/**
 * The PathFinder class provides functionality to find the shortest path between two points on a 
 * game map using the Breadth-First Search (BFS) algorithm.
 * It maintains a list of positions that constitute the path to the target.
 */
public final class PathFinder {
    
    private static ArrayList<int[]> path = new ArrayList<>(); //ArrayList de tableau de int représentant les positions constituant le chemin vers la cible
    private static int[][] movement = { //déplacement possible du joueur
        {0, 1}, // en haut
        {1, 0}, // en bas
        {0, -1}, // à gauche
        {-1, 0} // à droite
    };
    public static ArrayList<int[]> getPath() {
        return path;
    }
    /**
     * Méthode permettant de trouver le chemin le plus court entre deux points.
     * Le chemin trouver sera stocké dans l'ArrayList chemin
     * @param map La carte du jeu 
     * @param start la position de départ qui est un couple de int ([ligne][colonne])
     * @param end la position de d'arrivée qui est un couple de int ([ligne][colonne])
     * @return true si le chemin a été trouvé, false sinon
     */
    public static boolean findShortPath(GameMap map, int[] start, int[] end) {
        path.clear();
        Queue<int[]> file = new LinkedList<>();

        // Une Map pour stocké les positions précédentes pour le backtracking
        Map<String, int[]> prec = new HashMap<>();

        // Tableau des cases marquées comme visité par l'algorithme
        boolean[][] visit = new boolean[map.getHeight()][map.getWidth()];

        // Initialisation de l'algorithme
        file.add(start);
        visit[start[0]][start[1]] = true;

        while (!file.isEmpty()) {
            int[] current = file.poll();

            // Si on atteint la position d'arrivée en reconstruit le chemin
            if (current[0] == end[0] && current[1] == end[1]) {
                rebuildPath(prec, start, end);
                return true;
            }

            // Exploration des voisins
            for (int[] dir : movement) {
                int nLine = current[0] + dir[0];
                int nCol = current[1] + dir[1];

                if (isValid(nLine, nCol, map, visit)) {
                    file.add(new int[] {nLine, nCol});
                    visit[nLine][nCol] = true;
                    prec.put(nLine + "," + nCol, current);
                }
            }
        }
        return false;
    }
    /**
     * Méthode permettant de reconstruire le chemin trouvé dans l'ArrayList chemin
     * @param prec une Map des positions précédentes visitées
     * @param start la position de départ
     * @param end la position d'arrivée
     */
    private static void rebuildPath(Map<String, int[]> prec, int[] start, int[] end) {
        int[] current = end;

        while (!Arrays.equals(current, start)) {
            path.add(current);
            current = prec.get(current[0] + "," + current[1]);
        }

        path.add(start);
        Collections.reverse(path);
    }
    
    /**
     * Méthode permettant d'indiquer si la case est une case est accessible
     * @param line la position de la case par rapport à la ligne ou elle est
     * @param col la position de la case par rapport à la colonne ou elle est
     * @param map La carte du jeu
     * @param visité Le tableau indiquant qu'elles cases ont été visités
     * @return true si la case est accéssible, false sinon
     */
    private static boolean isValid(int line, int col, GameMap map, boolean[][] visité) {
        return line >= 0 && line < map.getHeight() && col >= 0 
        && col < map.getWidth() && (map.getMap()[line][col].getType() == TileType.EMPTY || 
        map.getMap()[line][col].getType() == TileType.START ||
         map.getMap()[line][col].getType() == TileType.RESOURCETMP) 
        && !visité[line][col];
    }
}

