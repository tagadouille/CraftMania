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
 * 
 * @author Dai Elias
 */
public final class PathFinder {
    
    private static ArrayList<int[]> path = new ArrayList<>();

    private static int[][] movement = {
        {0, 1},
        {1, 0},
        {0, -1},
        {-1, 0}
    };

    public static ArrayList<int[]> getPath() {
        return path;
    }

    /**
     * Finds the shortest path from the start position to the end position on the given game map.
     * @param map The game map on which to find the path
     * @param start The starting position as an array of two integers [x, y]
     * @param end The target position as an array of two integers [x, y]
     * @return true if a path is found, false otherwise
     */
    @SuppressWarnings("exports")
    public static boolean findShortPath(GameMap map, int[] start, int[] end) {
        path.clear();
        Queue<int[]> file = new LinkedList<>();

        Map<String, int[]> prev = new HashMap<>();

        boolean[][] visit = new boolean[map.getHeight()][map.getWidth()];

        file.add(start);
        visit[start[0]][start[1]] = true;

        while (!file.isEmpty()) {
            int[] current = file.poll();

            // If we reached the target, the path is reconstruct and return true
            if (current[0] == end[0] && current[1] == end[1]) {
                rebuildPath(prev, start, end);
                return true;
            }

            // Neighbor exploration
            for (int[] dir : movement) {
                int nLine = current[0] + dir[0];
                int nCol = current[1] + dir[1];

                if (isValid(nLine, nCol, map, visit)) {
                    file.add(new int[] {nLine, nCol});
                    visit[nLine][nCol] = true;
                    prev.put(nLine + "," + nCol, current);
                }
            }
        }
        return false;
    }
    
    private static void rebuildPath(Map<String, int[]> prec, int[] start, int[] end) {
        int[] current = end;

        while (!Arrays.equals(current, start)) {
            path.add(current);
            current = prec.get(current[0] + "," + current[1]);
        }

        path.add(start);
        Collections.reverse(path);
    }
    
    
    private static boolean isValid(int line, int col, GameMap map, boolean[][] visit) {

        return line >= 0 && line < map.getHeight() && col >= 0 
        && col < map.getWidth() && (map.getMap()[line][col].getType() == TileType.EMPTY || 
        map.getMap()[line][col].getType() == TileType.START ||
         map.getMap()[line][col].getType() == TileType.RESOURCETMP) 
        && !visit[line][col];
    }
}

