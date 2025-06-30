package main.java.com.models.map;

import java.util.Random;

import main.java.com.models.Ressources.RessourceEnum;

public class Map {
    private Tile[][] map;
    private int width;
    private int height;

    public Map(int width, int height){
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
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.println(map[i][j].getType());
            }
        }
    }
    /**
     * Method for generate the ressources of the map
     */
    private void generateRessource(){
        Random rand = new Random();

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
        }
    }
    /**
     * Method for generate the market of the map
     */
    private void generateMarket(){
        Random rand = new Random();

        //If the market is in a row
        if(rand.nextInt(2) == 0){
            int i = height - 1;
            if(rand.nextInt(2) == 0){
                i = 0;
            }
            map[i][rand.nextInt(width)] = new Tile(TileType.MARKET);
        }else{//If the market is in a col
            int j = width - 1;
            if(rand.nextInt(2) == 0){
                j = 0;
            }
            map[rand.nextInt(height)][j] = new Tile(TileType.MARKET);
        }
    }
}
