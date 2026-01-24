package com.app.main.views;

import com.app.main.controllers.GameController;
import com.app.main.controllers.input.KeyHandler;
import com.app.main.controllers.input.MouseController;
import com.app.main.util.Point;
import com.app.main.util.design_pattern.Observable;
import com.app.main.util.design_pattern.Observer;
import com.app.main.views.props_display.MapDisplay;
import com.app.main.views.props_display.PlayerSprite;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * The GameView class represents the main view of the game.
 * It handles rendering the game map, player sprite, and other UI elements.
 * Implements the Observer pattern to update the view based on game events.
 * @see Observer
 * @author Dai Elias
 */
public final class GameView extends StackPane implements Observer{

    private Canvas canvas;
    private double width;
    private double height;

    private AnimationTimer gameLoop;

    private static double multiplicator = 2;
    public static final int TILE_SIZE = 16;
    private static double spriteSize = multiplicator*TILE_SIZE;

    private GameController gameController;

    private MapDisplay mapDisplay;
    private PlayerSprite sprite;

    private HarvestBar harvestBar = new HarvestBar();


    private GameView(Scene scene, double width, double height, GameController gameController, KeyHandler keyHandler) {
        super();
        
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        this.gameController = gameController;
        gameController.getPlayerController().setGameView(this);

        this.sprite = PlayerSprite.createPlayerSprite(gameController.getPlayer());

        this.mapDisplay = MapDisplay.createMapDisplay(gameController.getGameMap().getMap());

        keyHandler.setupInput(scene);

        MouseController mouseController = MouseController.create(
            gameController.getGameMap(),
            gameController.getPlayer(),
            gameController.getPlayerController()
        );

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> mouseController.setUp(e.getX(), e.getY()));

        startGameLoop();
        canvas.requestFocus();
    }

    /**
     * The factory method for creating a GameView instance.
     * @param scene the scene where the gameview is display
     * @param width the width of the gameview
     * @param height the height of the gameview
     * @param gameController the game controller associated with the gameview
     * @param keyHandler the key handler for managing keyboard input
     * @return a new GameView instance
     */
    public static GameView createGameView(Scene scene, double width, double height, GameController gameController, KeyHandler keyHandler){

        if(scene == null) {
            throw new IllegalArgumentException("The scene can't be null");
        }
        if(width <= 0) {
            throw new IllegalArgumentException("The width must be positive");
        }
        if(height <= 0) {
            throw new IllegalArgumentException("The height must be positive");
        }
        if(gameController == null) {
            throw new IllegalArgumentException("The gameController can't be null");
        }
        if(keyHandler == null) {
            throw new IllegalArgumentException("The keyhandler can't be null");
        }
        return new GameView(scene, width, height, gameController, keyHandler);
    }

    /* Getters : */

    public static double getMultiplicator() {
        return multiplicator;
    }

    Canvas getCanvas() {
        return canvas;
    }

    public static double getSpriteSize() {
        return spriteSize;
    }

    public void addHarvestBar(){
        this.getChildren().addLast(harvestBar);
    }

    public void removeHarvestBar(){
        this.getChildren().remove(harvestBar);
    }

    /**
     * For the gameloop functionnement
     */
    private void startGameLoop() {

        gameLoop = new AnimationTimer() {

            // For run the game at max 30 FPS
            int fps = 30;
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

    @Override
    public void update(Observable o, Object arg, String action) {
        
        switch (action) {
            case "harvest":
                if(arg instanceof Integer) {
                    System.out.println("harvest");
                    this.harvestBar.decrease(((Integer) arg).intValue());
                }
                break;
            case "harvest-sp":
                if(arg instanceof Point){
                    this.harvestBar.spawn(((Point) arg).x(), ((Point) arg).y(), this);
                    System.out.println("spawn");
                }
            case "harvest-dp":
                System.out.println("despawn");
                this.harvestBar.despawn(this);
            default:
                break;
        }
    }
}