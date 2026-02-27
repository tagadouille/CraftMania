package com.app.main.views;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

/**
 * The GameBar class represents the top bar of the game scene, 
 * providing buttons for quitting, saving, and accessing the inventory.
 * It extends the HBox class from JavaFX and includes methods 
 * for displaying notifications related to saving the game.
 * 
 * @author Dai Elias
 */
public final class GameBar extends HBox {

    private Button quit = new Button("Quit");
    private Button save = new Button("Save");

    private Button inventory = new Button("Inventory");

    /**
     * Constructor for GameBar.
     * Initializes the game bar with the quit, save, and inventory buttons.
     */
    public GameBar() {
        super(5);

        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(quit, save, inventory);
    }

    /* Getters : */

    public Button getQuit() {
        return quit;
    }

    public Button getSave() {
        return save;
    }

    public Button getInventory() {
        return inventory;
    }

    /**
     * This method displays a notification alert based on the success of saving the game.
     * @param success a boolean indicating whether the game was saved successfully or not
     */
    public void saveNotif(boolean success) {

        if(success) {
            Alert alert = new Alert(AlertType.INFORMATION, "The game was saved with success ! ", ButtonType.OK);
            alert.setHeaderText("Saving");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR, "An error occured  while saving the game", ButtonType.OK);
            alert.setHeaderText("Saving");
            alert.showAndWait();
        }
    }
    
}
