package com.app.main.views.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The MainMenu class represents the main menu scene of the application.
 * It provides buttons for starting a new game, loading a save, accessing settings, and quitting the application.
 * 
 * @author Dai Elias
 */
public final class MainMenu extends Scene {

    private Button newGame = new Button("New Game");
    private Button quit = new Button("Quit");
    private Button settings = new Button("Settings");
    private Button load = new Button("Load save");

    private MainMenu(int size) {
        super(new HBox(0));
        HBox root = (HBox) this.getRoot();

        root.setPrefHeight(size);
        root.setPrefWidth(size);
        root.setAlignment(Pos.CENTER);

        root.getStyleClass().add("menu");

        VBox menuBox = new VBox(10);

        menuBox.setAlignment(Pos.CENTER);

        menuBox.getChildren().addAll(newGame, load, settings, quit);
        root.getChildren().add(menuBox);
    }

    /**
     * Creates a MainMenu instance with the specified width and height.
     * @param size the width and the height of the main menu
     * @return a new MainMenu instance
     */
    public static MainMenu create(int size) {

        if(size <= 0) {
            throw new IllegalArgumentException("The width can't be null");
        }
        return new MainMenu(size);
    }

    public Button getNewGame() {
        return newGame;
    }

    public Button getSettings() {
        return settings;
    }

    public Button getLoad() {
        return load;
    }

    public Button getQuit() {
        return quit;
    }

    /**
     * This method displays a notification alert based on the success of loading a save.
     * @param success a boolean indicating whether the save was loaded successfully or not
     */
    public void loadNotif(boolean success) {

        if(success) {
            Alert alert = new Alert(AlertType.INFORMATION, "The save was loaded with success ! ", ButtonType.OK);
            alert.setHeaderText("Loading");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR, "An error occured while loading the save", ButtonType.OK);
            alert.setHeaderText("File corrupted or not found");
            alert.showAndWait();
        }
    }

}
