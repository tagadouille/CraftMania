package com.app.main.views.props_display;

import com.app.main.models.map.Tile;
import com.app.main.models.map.Tile.TileType;
import com.app.main.views.GameView;
import com.app.main.views.utilities.ResourceImageEnum;
import com.app.main.views.utilities.TileImageEnum;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class used to display the map tiles.
 */
public final class MapDisplay {

    private Tile[][] map;

    private MapDisplay(Tile[][] map){
        this.map = map;
    }

    /**
     * Static factory method to create a MapDisplay instance.
     * @param map the map to display
     * @return a MapDisplay instance
     */
    public static MapDisplay createMapDisplay(Tile[][] map){

        if(map == null){
            throw new IllegalArgumentException("MapDisplay.of: map cannot be null");
        }
        if(map.length == 0 || map[0].length == 0){
            throw new IllegalArgumentException("MapDisplay.of: map cannot be empty");
        }
        return new MapDisplay(map);
    }

    public Tile[][] getMap() {
        return map;
    }

    /**
     * Sets the map to display.
     * @param map the map to display
     */
    public void setMap(Tile[][] map) {

        if(map == null){
            throw new IllegalArgumentException("MapDisplay.of: map cannot be null");
        }
        if(map.length == 0 || map[0].length == 0){
            throw new IllegalArgumentException("MapDisplay.of: map cannot be empty");
        }
        this.map = map;
    }

    /**
     * Displays the map on the given GraphicsContext.
     * @param g the GraphicsContext to draw on
     */
    public void displayMap(GraphicsContext g){

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                
                Image img = null;
                Tile tile = map[i][j];

                if(tile.getType() != TileType.HARVESTER && tile.getType() != 
                    TileType.FACTORY && tile.getType() != TileType.RESOURCE){
                    
                    for(TileImageEnum type : TileImageEnum.values()){
                        if(tile.getType().toString().equals(type.toString())){
                            img = type.getImage();
                            break;
                        }
                    }
                }

                // Display resource : 
                else if(tile.getType() == TileType.RESOURCE){
                    
                    for(ResourceImageEnum type : ResourceImageEnum.values()){
                        if(tile.getItem().getName().equals(type.toString())){
                            img = type.getImage();
                            break;
                        }
                    }
                }

                if(img != null){
                    new Sprite(j * GameView.TILE_SIZE, i * GameView.TILE_SIZE, GameView.TILE_SIZE, GameView.TILE_SIZE, img).display(g);
                }
            }
        }
    }
}