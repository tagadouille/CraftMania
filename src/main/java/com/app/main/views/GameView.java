package com.app.main.views;

import com.app.main.controllers.GameController;
import com.app.main.controllers.KeyHandler;
import com.app.main.controllers.MouseController;
import com.app.main.util.Observable;
import com.app.main.util.Observer;
import com.app.main.util.Point;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public final class GameView extends StackPane implements Observer{

    private Canvas canvas;
    private double width;
    private double height;

    private AnimationTimer gameLoop;

    private static double multiplicator = 2;
    static final int TILE_SIZE = 16;
    private static double spriteSize = multiplicator*TILE_SIZE;

    private GameController gameController;

    private MapDisplay mapDisplay;
    private PlayerSprite sprite;

    private HarvestBar harvestBar = new HarvestBar();


    public GameView(Scene scene, double width, double height, GameController gameController, KeyHandler keyHandler) {
        super();
        
        this.width = width;
        this.height = height;
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);

        this.gameController = gameController;
        gameController.getPlayerController().setGameView(this);

        this.sprite = new PlayerSprite(gameController.getPlayer());

        this.mapDisplay = new MapDisplay(gameController.getGameMap().getMap());

        keyHandler.setupInput(scene);

        MouseController mouseController = new MouseController(gameController.getGameMap(), gameController.getPlayer(), gameController.getPlayerController());

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> mouseController.setUp(e.getX(), e.getY()));

        startGameLoop();
        canvas.requestFocus();
    }

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