package com.app.main.controllers;

import java.util.ArrayList;
import java.util.List;

import com.app.main.PathFinder;
import com.app.main.controllers.input.KeyHandler;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.resources.ResourceEnum;
import com.app.main.util.Point;
import com.app.main.util.design_pattern.Observable;
import com.app.main.util.design_pattern.Observer;
import com.app.main.views.GameView;

import javafx.scene.input.KeyCode;

/**
 * This class represents the controller of the player
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
    public void harvest(Tile tile){
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
        }else{
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
        }else{
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
     * Méthode permettant de faire déplacer le joueur vers la position trouvée
     * par la classe PathFinder
     */
    public void movementToPos(){
        go = true;
        //Si on a atteint la destination
        if(pathIndex ==  PathFinder.getPath().size()){
            pathIndex = 0;
            go = false;
        }
        double px = (double) Math.round(player.getX() * 100) / 100;
        double py = (double) Math.round(player.getY() * 100) / 100;

        System.out.println(px + " " + py);
        int[] target = PathFinder.getPath().get(pathIndex);

        if(pathIndex == 0 && px == target[1] && py == target[0]){
            pathIndex++;
            return;
        }
        int x = target[1];
        int y = target[0];

        //Si on a pas atteint la position
        if(px != x || py != y){

            if(px != x){
                //on se déplace en x vers l'objectif
                if(px > x){
                    player.moveLeft();
                }else{
                    player.moveRight();
                }
            }else{
                //On se déplace en y vers l'objectif
                if(py > y){
                    player.moveUp();
                }else{
                    player.moveDown();
                }
            }
        }if(px == x && py == y){
            pathIndex++;
        }
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}
