package com.app.main.views;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

public class HarvestBar extends ProgressBar {
    
    public HarvestBar() {
        
        this.setProgress(0);
        this.getStyleClass().add("harvest-bar");

        /*super(JProgressBar.HORIZONTAL,0, 100);
        this.setString("Récolte en cours..");*/
    }
    /**
     * For add the harvest bar in the pane and reset the value
     * @param pane
     */
    public void spawn(int x, int y, GameView gameView){

        this.setProgress(100);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefSize(GameView.getSpriteSize()*2, GameView.getSpriteSize()/2);
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
        this.setProgress(this.getProgress() - amount);
    }
}