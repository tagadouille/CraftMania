package com.app.main.views;

import com.app.main.models.Player;
import com.app.main.util.ImageLoader;

import javafx.scene.canvas.GraphicsContext;

public class PlayerSprite extends Sprite{
    
    private Player player;

    public PlayerSprite(Player player){
        super(0, 0, GameView.TILE_SIZE, GameView.TILE_SIZE, ImageLoader.loadImage("src/ressources/image/player.png"));
        this.player = player;
                this.updateCord();
    }

    public void updateCord(){
        this.setPosX((int) (player.getX()*GameView.TILE_SIZE));
        this.setPosY((int) (player.getY()*GameView.TILE_SIZE));
    }

    @Override
    public void display(GraphicsContext g){
        updateCord();
        super.display(g);
    }
}
