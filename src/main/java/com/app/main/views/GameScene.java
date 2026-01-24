package com.app.main.views;

import com.app.main.controllers.GameController;
import com.app.main.controllers.PlayerController;
import com.app.main.controllers.input.KeyHandler;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class GameScene extends Scene{

    private VBox parent;

    public GameScene() {
        super(new VBox());
        this.parent = (VBox) this.getRoot();

        KeyHandler keyHandler = new KeyHandler();

        GameMap gameMap = new GameMap(15, 15);
        PlayerController playerController = new PlayerController(new Player(7, 7), keyHandler);

        GameView gameView = new GameView(this, 480,480, new GameController(playerController, gameMap), keyHandler);

        parent.getChildren().add(gameView);
    }
}
