package com.app.main.models.map;

import java.awt.Point;
import java.util.Random;

import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Harvester;
import com.app.main.models.machine.Machine;
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

        // Starting with an empty map
        map = new Tile[width][height];

                for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Tile.createTile(TileType.EMPTY, null);
            }
        }
        map[height/2][width/2] = Tile.createTile(TileType.START, null);
    }

    /**
     * Factory method to create a default GameMap with size 15x15
     * @param generate whether to generate the map with resources and market or not
     * @return the created map
     */
    public static GameMap createDefaultMap(boolean generate){

        GameMap gameMap = new GameMap(15, 15);

        if(generate){
            gameMap.generateMap();
        }
        return gameMap;
    }

    /**
     * Factory method to create a GameMap with given width and height
     * @param width the width of the map
     * @param height the height of the map
     * @param generate whether to generate the map with resources and market or not
     * @return the created map
     */
    public static GameMap createMap(int width, int height, boolean generate){

        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and height must be positive integers.");
        }

        GameMap gameMap = new GameMap(width, height);

        if (generate) {
            gameMap.generateMap();
        }
        return gameMap;
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
     * Setter for the market position on the map. 
     * It checks if the new position is valid and updates the map accordingly.
     * @param marketPos the new position of the market
     */
    public void setMarketPos(Point marketPos) {

        if(marketPos == null){
            throw new IllegalArgumentException("Market position cannot be null.");
        }

        if(!this.inBound(marketPos.x, marketPos.y)){
            throw new IllegalArgumentException("Market position must be within the bounds of the map.");
        }

        // Change the position of the market on the map
        this.map[this.marketPos.y][this.marketPos.x].setType(TileType.EMPTY);
        this.marketPos = marketPos;
        this.map[marketPos.y][marketPos.x].setType(TileType.MARKET);
    }

    /**
     * Method which generate the map
     */
    private void generateMap(){
        generateMarket();
        generateResource();
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
                }
                while(map[i][j].getType() != TileType.EMPTY);

                if(resType.isTmp()){
                    map[i][j] = Tile.createTile(TileType.RESOURCETMP, resType.getResource());
                }
                else{
                    map[i][j] = Tile.createTile(TileType.RESOURCE, resType.getResource());
                }
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
    private void generateMarket() {

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
        }
        else{//If the market is in a col
            int j = width - 1;
            if(rand.nextInt(2) == 0){
                j = 0;
            }
            y = rand.nextInt(height);
            x = j;
        }
        setMarketPos(new Point(x, y));
    }
    
    /**
     * Method to check if the given coordinates are within the bounds of the map
     * @param x the x coordinate to check
     * @param y the y coordinate to check
     * @return true if the coordinates are within the bounds of the map, false otherwise
     */
    public boolean inBound(int x, int y){
        return x >= 0 && x < this.width && y >=0 && y < this.height;
    }

    /**
     * Method to add a machine on the map at the given coordinates
     * @param x the x coordinate where the machine should be added
     * @param y the y coordinate where the machine should be added
     * @param machine the machine to add on the map
     */
    public void addMachine(int x, int y, Machine machine){

        if(!inBound(x, y)){
            throw new IllegalArgumentException("Coordinates must be within the bounds of the map.");
        }

        if(machine == null){
            throw new IllegalArgumentException("Machine cannot be null.");
        }

        if(machine instanceof Harvester){
            map[x][y] = Tile.createTile(TileType.HARVESTER, machine);
        }
        else if(machine instanceof Factory){
            map[x][y] = Tile.createTile(TileType.FACTORY, machine);
        }
        else{
            throw new IllegalArgumentException("Unsupported machine type.");
        }
    }
}
