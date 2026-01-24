package com.app.main.views.props_display;

import com.app.main.models.map.Tile;
import com.app.main.models.map.TileType;
import com.app.main.views.GameView;
import com.app.main.views.utilities.RessourceImageEnum;
import com.app.main.views.utilities.TileImageEnum;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapDisplay {

    private Tile[][] map;

    public MapDisplay(Tile[][] map){
        this.map = map;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }

    public void displayMap(GraphicsContext g){

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                
                Image img = null;
                Tile tile = map[i][j];

                if(tile.getType() != TileType.HARVESTER && tile.getType() != 
                    TileType.FACTORY && tile.getType() != TileType.RESSOURCE){
                    
                    for(TileImageEnum type : TileImageEnum.values()){
                        if(tile.getType().toString().equals(type.toString())){
                            img = type.getImage();
                            break;
                        }
                    }
                }

                // Display ressource : 
                else if(tile.getType() == TileType.RESSOURCE){
                    
                    for(RessourceImageEnum type : RessourceImageEnum.values()){
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