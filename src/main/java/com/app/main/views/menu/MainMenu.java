package com.app.main.views.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The MainMenu class represents the main menu scene of the application.
 * It provides buttons for starting a new game, loading a save, accessing settings, and quitting the application.
 * 
 * @author Dai Elias
 */
public class MainMenu extends Scene {

    private Button newGame = new Button("New Game");
    private Button quit = new Button("Quit");
    private Button settings = new Button("Settings");
    private Button load = new Button("Load save");

    private MainMenu(int width, int height) {
        super(new HBox(0));
        HBox root = (HBox) this.getRoot();

        root.setPrefHeight(height);
        root.setPrefWidth(width);
        root.setAlignment(Pos.CENTER);

        root.getStyleClass().add("menu");

        VBox menuBox = new VBox(10);

        menuBox.setAlignment(Pos.CENTER);

        menuBox.getChildren().addAll(newGame, load, settings, quit);
        root.getChildren().add(menuBox);
    }

    /**
     * Creates a MainMenu instance with the specified width and height.
     * @param width the width of the main menu
     * @param height the height of the main menu
     * @return a new MainMenu instance
     */
    public static MainMenu create(int width, int height) {

        if(width <= 0) {
            throw new IllegalArgumentException("The width can't be null");
        }

        if(height <= 0) {
            throw new IllegalArgumentException("The height can't be null");
        }
        return new MainMenu(width, height);
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

}
