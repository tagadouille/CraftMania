package com.app.main.models.map;

import java.awt.Point;
import java.util.Random;

import com.app.main.models.machine.Harvester;
import com.app.main.models.map.Tile.TileType;
import com.app.main.models.resources.ResourceEnum;

/**
 * Class GameMap representing the game map
 * @author Dai Elias
 */
public final class GameMap {

    private Tile[][] map;
    private int width;
    private int height;

    private Point marketPos = new Point();

    private GameMap(int width, int height){
        this.height = height;
        this.width = width;
        map = new Tile[width][height];
        generateMap();
    }

    /**
     * Factory method to create a default GameMap with size 15x15
     * @return the created map
     */
    public static GameMap createDefaultMap(){
        return new GameMap(15, 15);
    }

    /**
     * Factory method to create a GameMap with given width and height
     * @param width the width of the map
     * @param height the height of the map
     * @return the created map
     */
    public static GameMap createMap(int width, int height){

        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and height must be positive integers.");
        }
        return new GameMap(width, height);
    }

    /* Getters : */
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
                map[i][j] = Tile.createTile(TileType.EMPTY, null);
            }
        }
        map[height/2][width/2] = Tile.createTile(TileType.START, null);
        generateMarket();
        generateResource();

        map[2][2].setItem(Harvester.createSimpleHarvester());
        map[2][2].setType(TileType.HARVESTER);
        map[2][1].setType(TileType.RESOURCE);
        map[2][1].setItem(ResourceEnum.WOOD.getResource());
    }
    /**
     * Method for generate the resources of the map
     */
    private void generateResource(){
        Random rand = new Random();
        int t = 0;

        for (ResourceEnum resType : ResourceEnum.values()) {
            for (int k = 0; k < 2; k++) {
                int i;
                int j;

                do{
                    i = rand.nextInt(height);
                    j = rand.nextInt(width);
                }while(map[i][j].getType() != TileType.EMPTY);
                map[i][j] = Tile.createTile(TileType.RESOURCE, resType.getResource());
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
        map[6][6] = Tile.createTile(TileType.MARKET, null);
        marketPos = new Point(6, 6);
    }
    
    public boolean inBound(int x, int y){
        return x >= 0 && x < this.width && y >=0 && y < this.height;
    }
}
