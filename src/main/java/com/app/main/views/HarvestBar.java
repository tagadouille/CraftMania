package com.app.main.views;

import com.app.main.views.props_display.Sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * The HarvestBar class represents a progress bar used to indicate the progress of a harvesting action in the game.
 * It extends Sprite and provides methods to spawn, despawn, 
 * and decrease the progress of the harvest bar.
 * @see Sprite
 * @author Dai Elias
 */
public class HarvestBar extends Sprite {

    private int progress = 0;

    /**
     * Constructor for the HarvestBar class, initializes the progress to 0 
     * and sets up the progress bar.
     * The progress bar is initialized with a width of 50 and a height of 10.
     */
    public HarvestBar() {
        super(0, 0, 50, 10, null);
    }

    public int getProgress() {
        return progress;
    }

    /**
     * Sets the progress of the harvest bar, ensuring that the value is between 0 and 100.
     * @param progress the new progress value to be set for the harvest bar
     */
    public void setProgress(int progress) {

        this.progress = Math.min(Math.max(0, progress), 100);
    }
    
    /**
     * For add the harvest bar in the pane and reset the value
     * @param pane
     */
    public void spawn(int x, int y, GameView gameView){

        this.setProgress(0);
        this.setPosX((int) (x * GameView.getSpriteSize()));
        this.setPosY((int) (y * GameView.getSpriteSize()) - 15);
        gameView.addHarvestBar();
    }

    /**
     * For remove the harvest bar of the pane
     * @param gameView
     */
    public void despawn(GameView gameView){
        gameView.removeHarvestBar();
    }

    /**
     * For decrease the value of the harvest bar in function of the amount
     * @param amount
     */
    public void decrease(int amount){
        this.setProgress(amount);
    }

    /**
     * Displays the harvest bar on the screen using the provided GraphicsContext.
     * @param gc the GraphicsContext used to draw the harvest bar on the screen
     */
    public void display(GraphicsContext gc) {

        gc.setFill(Color.GRAY);
        gc.fillRect(getPosX(), getPosY(), getWidth(), getHeight());

        gc.setFill(Color.GREEN);
        gc.fillRect(getPosX(), getPosY(), getWidth() * (getProgress() / 100.0) , getHeight());
        
        gc.setStroke(javafx.scene.paint.Color.BLACK);
        gc.strokeRect(getPosX(), getPosY(), getWidth(), getHeight());
    }
}