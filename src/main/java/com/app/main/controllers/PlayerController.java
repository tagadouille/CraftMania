package com.app.main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.main.PathFinder;
import com.app.main.controllers.input.KeyHandler;
import com.app.main.models.Item;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.resources.Resource;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.models.resources.ResourceTMP;
import com.app.main.util.Point;
import com.app.main.util.design_pattern.Observable;
import com.app.main.util.design_pattern.Observer;
import com.app.main.views.GameView;

import javafx.scene.input.KeyCode;

/**
 * This class represents the controller of the player. 
 * It is responsible for handling the player's movements and actions, 
 * such as harvesting resources. 
 * It also implements the Observable interface to notify the GameView of any changes in the player's state.
 * 
 * @see Player
 * @see GameView
 * @see KeyHandler
 * @see Observable
 * 
 * @author Dai Elias
 */
public final class PlayerController implements Observable{
    
    private Player player;

    /* Harvest : */
    private boolean harvest = false;

    public static final long harvestTime = 5000;
    private long stopHarvestTime;
    private ResourceEnum resourceTypeHarvest;

    /* Movements : */
    private int pathIndex = 0;
    private boolean go = false;
    private static final double EPSILON = 0.01; // Small threshold to determine if the player has reached the target position

    private KeyHandler keyHandler;

    private List<Observer> observers = new ArrayList<>();

    private PlayerController(Player player, KeyHandler keyHandler){
        this.player = player;
        this.keyHandler = keyHandler;
    }

    /**
     * Factory method to create a PlayerController
     * @param player Player the player to control
     * @param keyHandler KeyHandler the key handler
     * @return PlayerController the player controller
     */
    public static PlayerController createPlayerController(Player player, KeyHandler keyHandler){
        return new PlayerController(player, keyHandler);
    }

    /* Getters : */
    public boolean isHarvest() {
        return harvest;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Set the game view as observer
     * @param gameView GameView the game view
     */
    public void setGameView(GameView gameView) {

        if(gameView == null){
            throw new IllegalArgumentException("GameView cannot be null");
        }
        this.observers.add(gameView);
    }

    /**
     * For harvest the resource in a tile
     * @param tile Tile the tile where the resource is
     */
    public void harvest(Tile tile) {

        Optional<Resource> resOpt = tile.getResource();
        if(!resOpt.isPresent()){
            return;
        }
        if(resOpt.get() instanceof ResourceTMP){
            ResourceTMP resTmp = (ResourceTMP) resOpt.get();
            if(!resTmp.isRespawned()){
                return;
            }
            resTmp.pick(this.player);
            return;
        }
        harvest = true;
        stopHarvestTime = System.currentTimeMillis() + harvestTime;
        resourceTypeHarvest = ResourceEnum.getResourceEnum(tile.getItem().getName());
        observers.get(0).update(this, new Point((int) player.getX(), (int) player.getY()), "harvest-sp");
    }

    private void stuckInHarvest(){
        long currentTime = System.currentTimeMillis();

        observers.get(0).update(this, (int) ((stopHarvestTime - currentTime) / 50), "harvest");

        if(currentTime >= stopHarvestTime){
            harvest = false;
            player.addResource(resourceTypeHarvest);
            this.observers.get(0).update(this, null, "harvest-dp");
        }

    }
    /**
     * Handle the behavior of the player
     * @param map
     */
    public void process(GameMap map){
        if(harvest){
            stuckInHarvest();
        }
        else{
            globalMove(map);
        }
    }
    /**
     * handle the movement of the player in fonction of wich key is pressed
     * @param map
     */
    private void globalMove(GameMap map){
        if(go){
            this.movementToPos();
        }
        else{
            if(keyHandler.keylist.contains(KeyCode.Z)){
                player.moveUp();
                if(player.isPositionIncorrect(map)){
                    player.moveDown();
                }
                return;
            }
            if(keyHandler.keylist.contains(KeyCode.S)){
                player.moveDown();
                if(player.isPositionIncorrect(map)){
                    player.moveUp();
                }
                return;
            }
            if(keyHandler.keylist.contains(KeyCode.Q)){
                player.moveLeft();
                if(player.isPositionIncorrect(map)){
                    player.moveRight();
                }
            }
            if(keyHandler.keylist.contains(KeyCode.D)){
                player.moveRight();
                if(player.isPositionIncorrect(map)){
                    player.moveLeft();
                }
                return;
            }
        }
    }

    /**
     * Handle the movement of the player to a position on the map
     */
    public void movementToPos() {

        if(pathIndex >= PathFinder.getPath().size()){
            pathIndex = 0;
            go = false;
            return;
        }

        go = true;

        int[] target = PathFinder.getPath().get(pathIndex);
        int targetX = target[1];
        int targetY = target[0];

        double px = player.getX();
        double py = player.getY();

        double dx = targetX - px;
        double dy = targetY - py;

        // Check if the player is close enough to the target position
        if(Math.abs(dx) < EPSILON && Math.abs(dy) < EPSILON){
            player.setX(targetX);
            player.setY(targetY);
            pathIndex++;
            return;
        }

        // Prioritize horizontal movement if the player is not close enough to the target position
        if(Math.abs(dx) > EPSILON){
            if(dx > 0){
                player.moveRight();
            } else {
                player.moveLeft();
            }
        }
        else if(Math.abs(dy) > EPSILON){
            if(dy > 0){
                player.moveDown();
            } else {
                player.moveUp();
            }
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
