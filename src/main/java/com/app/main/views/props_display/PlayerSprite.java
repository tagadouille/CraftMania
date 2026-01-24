package com.app.main.views.props_display;

import com.app.main.models.Player;
import com.app.main.util.image.ImageLoader;
import com.app.main.views.GameView;

import javafx.scene.canvas.GraphicsContext;

public class PlayerSprite extends Sprite{
    
    private Player player;

    public PlayerSprite(Player player){
        super(0, 0, GameView.TILE_SIZE, GameView.TILE_SIZE, ImageLoader.loadImage("src/main/resources/com/app/image/player.png"));
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
