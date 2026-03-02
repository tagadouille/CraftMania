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

    private GameScene(Player player, GameMap gameMap) {
        super(new VBox());
        this.parent = (VBox) this.getRoot();

        KeyHandler keyHandler = new KeyHandler();

        PlayerController playerController = PlayerController.createPlayerController(player, keyHandler);

        GameView gameView = GameView.createGameView(
            this, 
            480,
            480,
             GameController.createGameController(playerController, gameMap), keyHandler
        );

        GameBar gameBar = new GameBar();

        GameBarController.create(player, gameBar, gameMap);

        parent.getChildren().add(gameBar);
        parent.getChildren().add(gameView);
    }

    /**
     * Factory method to create a default GameScene instance
     *  with a new player and a default game map.
     * @return a new GameScene instance
     */
    public static GameScene create() {
        return new GameScene(Player.createPlayer(7, 7), GameMap.createDefaultMap(true));
    }

    /**
     * Factory method to create a GameScene instance with the specified player and game map.
     * @param player the player to be used in the game scene
     * @param gameMap the game map to be used in the game scene
     * @return a new GameScene instance with the specified player and game map
     */
    public static GameScene create(Player player, GameMap gameMap) {

        if(player == null) {
            throw new IllegalArgumentException("The player can't be null");
        }

        if(gameMap == null) {
            throw new IllegalArgumentException("The game map can't be null");
        }
        return new GameScene(player, gameMap);
    }
}
