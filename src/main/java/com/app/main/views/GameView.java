package com.app.main.views;

import com.app.main.controllers.GameController;
import com.app.main.controllers.KeyHandler;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;

public final class GameView extends StackPane {

    private Canvas canvas;
    private double width;
    private double height;

    private AnimationTimer gameLoop;

    private static double multiplicator = 1;
    static final int TILE_SIZE = 16;

    private GameController gameController;

    private MapDisplay mapDisplay;
    private PlayerSprite sprite;


    public GameView(Scene scene, double width, double height, GameController gameController, KeyHandler keyHandler) {
        super();
        
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        this.gameController = gameController;

        this.sprite = new PlayerSprite(gameController.getPlayer());

        this.mapDisplay = new MapDisplay(gameController.getGameMap().getMap());

        keyHandler.setupInput(scene);

        startGameLoop();
        canvas.requestFocus();
    }

    public static double getMultiplicator() {
        return multiplicator;
    }

    Canvas getCanvas() {
        return canvas;
    }

    /**
     * For the gameloop functionnement
     */
    private void startGameLoop() {

        gameLoop = new AnimationTimer() {
            // For run the game at max 60 FPS
            int fps = 60;
            double intervalMaj = 1000000000.0 / fps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long actualTime;

            @Override
            public void handle(long now) {
                actualTime = System.nanoTime();
                delta += (actualTime - lastTime) / intervalMaj;
                lastTime = actualTime;

                if(delta >= 1){

                    update();
                    render(canvas.getGraphicsContext2D());

                    delta--;
                }
            }
        };
        gameLoop.start();
    }

    /**
     * Update the model
     */
    private void update() {
        gameController.proceed();
    }

    /**
     * Update the view
     * @param gc the GraphicsContext of the canva
     */
    private void render(GraphicsContext gc) {

        gc.clearRect(0, 0, width, height);

        mapDisplay.displayMap(gc);
        sprite.display(gc);
    }
}