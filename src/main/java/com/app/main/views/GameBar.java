package com.app.main.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    
}
