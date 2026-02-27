package com.app.main.views;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;

public class GameBar extends HBox {

    private Button quit = new Button("Quit");
    private Button save = new Button("Save");

    private Button inventory = new Button("Inventory");

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
