package main.java.com.views;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import main.java.com.Screen;
import main.java.com.models.map.Tile;
import main.java.com.models.map.TileType;
import main.java.com.views.utilities.ImageLoader;
import main.java.com.views.utilities.RessourceImageEnum;
import main.java.com.views.utilities.TileImageEnum;

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
    public void displayMap(Graphics2D g){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                String imagePath = "";
                Tile tile = map[i][j];

                if(tile.getType() != TileType.HARVESTER && tile.getType() != TileType.FACTORY && tile.getType() != TileType.RESSOURCE){
                    for(TileImageEnum type : TileImageEnum.values()){
                        if(tile.getType().toString().equals(type.toString())){
                            imagePath = type.getImagePath();
                            break;
                        }
                    }
                }else if(tile.getType() == TileType.RESSOURCE){
                    for(RessourceImageEnum type : RessourceImageEnum.values()){
                        if(tile.getItem().getTypeName().equals(type.toString())){
                            imagePath = type.getImagePath();
                            break;
                        }
                    }
                }
                ImageIcon img = ImageLoader.loadImage(imagePath);

                if(img != null){
                    new Sprite(j * Screen.getTileSize(), i * Screen.getTileSize(), Screen.getTileSize(), Screen.getTileSize(), img.getImage()).display(g);
                }
            }
        }
    }
}
