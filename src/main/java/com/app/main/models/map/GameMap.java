package com.app.main.models.map;

import java.awt.Point;
import java.util.Random;

import com.app.main.models.ressources.RessourceEnum;

public class GameMap {
    private Tile[][] map;
    private int width;
    private int height;

    private Point marketPos = new Point();

    public GameMap(int width, int height){
        this.height = height;
        this.width = width;
        map = new Tile[width][height];
        generateMap();
    }

    public Tile[][] getMap() {
        return map;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Point getMarketPos() {
        return marketPos;
    }

    /**
     * Method which generate the map
     */
    private void generateMap(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(TileType.EMPTY);
            }
        }
        map[height/2][width/2] = new Tile(TileType.START);
        generateMarket();
        generateRessource();
    }
    /**
     * Method for generate the ressources of the map
     */
    private void generateRessource(){
        Random rand = new Random();
        int t = 0;

        for (RessourceEnum resType : RessourceEnum.values()) {
            for (int k = 0; k < 2; k++) {
                int i;
                int j;

                do{
                    i = rand.nextInt(height);
                    j = rand.nextInt(width);
                }while(map[i][j].getType() != TileType.EMPTY);
                map[i][j] = new Tile(TileType.RESSOURCE, resType.getRessource());
            }
            t++;
            if(t == 4){
                break;
            }
        }
    }
    /**
     * Method for generate the market of the map
     */
    private void generateMarket(){
        Random rand = new Random();
        int x, y;

        //If the market is in a row
        if(rand.nextInt(2) == 0){
            int i = height - 1;
            if(rand.nextInt(2) == 0){
                i = 0;
            }
            y = i;
            x = rand.nextInt(width);
        }else{//If the market is in a col
            int j = width - 1;
            if(rand.nextInt(2) == 0){
                j = 0;
            }
            y = rand.nextInt(height);
            x = j;
        }
        map[6][6] = new Tile(TileType.MARKET);
        marketPos = new Point(6, 6);
    }
    
    public boolean inBound(int x, int y){
        return x >= 0 && x < this.width && y >=0 && y < this.height;
    }
}
