package com.app.main.views;

import com.app.main.controllers.GameController;
import com.app.main.controllers.PlayerController;
import com.app.main.controllers.input.KeyHandler;
import com.app.main.controllers.view_controller.GameBarController;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * The GameScene class represents the main scene of the game.
 * Extends the JavaFX Scene class and sets up the game view and controllers.
 * @author Dai Elias
 */
public class GameScene extends Scene{

    private VBox parent;

    /**
     * Constructor for GameScene.
     */
    public GameScene() {
        super(new VBox());
        this.parent = (VBox) this.getRoot();

        KeyHandler keyHandler = new KeyHandler();

        Player player = Player.createPlayer(7, 7);

        GameMap gameMap = GameMap.createDefaultMap();
        PlayerController playerController = PlayerController.createPlayerController(player, keyHandler);

        GameView gameView = GameView.createGameView(
            this, 
            480,
            480,
             GameController.createGameController(playerController, gameMap), keyHandler
        );

        GameBar gameBar = new GameBar();

        GameBarController.create(player, gameBar);

        parent.getChildren().add(gameBar);
        parent.getChildren().add(gameView);
    }
}
