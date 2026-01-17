/*package main.java.com.views;

import java.awt.Graphics2D;

import main.java.com.Screen;
import main.java.com.models.Player;
import main.java.com.views.utilities.ImageLoader;

public class PlayerSprite extends Sprite{
    
    private Player player;

    public PlayerSprite(Player player){
        super(0, 0, Screen.getTileSize(), Screen.getTileSize(), ImageLoader.loadImage("src/ressources/image/player.png").getImage());
        this.player = player;
                this.updateCord();
    }
    public void updateCord(){
        this.setPosX((int) (player.getX()*Screen.getTileSize()));
        this.setPosY((int) (player.getY()*Screen.getTileSize()));
    }
    @Override
    public void display(Graphics2D g){
        updateCord();
        super.display(g);
    }
}*/
