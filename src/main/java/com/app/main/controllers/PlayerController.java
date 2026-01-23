package com.app.main.controllers;

import java.util.ArrayList;
import java.util.List;

import com.app.main.PathFinder;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.ressources.RessourceEnum;
import com.app.main.util.Observable;
import com.app.main.util.Observer;
import com.app.main.util.Point;
import com.app.main.views.GameView;

import javafx.scene.input.KeyCode;

public class PlayerController implements Observable{
    
    private Player player;
    private boolean harvest = false;

    public static final long harvestTime = 5000;
    private long stopHarvestTime;
    private RessourceEnum ressourceTypeHarvest;

    private int pathIndex = 0;
    private boolean go = false;

    private KeyHandler keyHandler;

    private List<Observer> observers = new ArrayList<>();

    public PlayerController(Player player, KeyHandler keyHandler){
        this.player = player;
        this.keyHandler = keyHandler;
    }

    public boolean isHarvest() {
        return harvest;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGameView(GameView gameView) {
        this.observers.add(gameView);
    }

    /**
     * For harvest the ressource in a tile
     * @param tile Tile the tile where the ressource is
     */
    public void harvest(Tile tile){
        harvest = true;
        stopHarvestTime = System.currentTimeMillis() + harvestTime;
        ressourceTypeHarvest = RessourceEnum.getRessourceEnum(tile.getItem().getName());
        observers.get(0).update(this, new Point((int) player.getX(), (int) player.getY()), "harvest-sp");
    }

    private void stuckInHarvest(){
        long currentTime = System.currentTimeMillis();

        observers.get(0).update(this, (int) ((stopHarvestTime - currentTime) / 50), "harvest");

        if(currentTime >= stopHarvestTime){
            harvest = false;
            player.addRessource(ressourceTypeHarvest);
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
