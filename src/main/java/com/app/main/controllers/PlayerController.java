package com.app.main.controllers;

import com.app.main.PathFinder;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.ressources.RessourceEnum;

public class PlayerController{
    
    private Player player;
    private boolean harvest = false;

    //private Screen screen;

    public static final long harvestTime = 5000;
    private long stopHarvestTime;
    private RessourceEnum ressourceTypeHarvest;

    private int pathIndex = 0;
    private boolean go = false;

    /*public PlayerController(Player player, Screen screen){
        this.player = player;
        this.screen = screen;
    }
    public boolean isHarvest() {
        return harvest;
    }**/
    /**
     * For harvest the ressource in a tile
     * @param tile Tile the tile where the ressource is
     */
    public void harvest(Tile tile){
        harvest = true;
        stopHarvestTime = System.currentTimeMillis() + harvestTime;
        ressourceTypeHarvest = RessourceEnum.getRessourceEnum(tile.getItem().getName());
        //screen.spawnHarvestBar();
    }
    private void stuckInHarvest(){
        long currentTime = System.currentTimeMillis();

        /*screen.getHarvestBar().setValue((int) ((stopHarvestTime - currentTime) / 50));

        if(currentTime >= stopHarvestTime){
            harvest = false;
            player.addRessource(ressourceTypeHarvest);
            screen.despawnHarvestBar();
        }*/

    }
    /**
     * Handle the behavior of the player
     * @param keyHandler
     * @param map
     */
    public void process(KeyHandler keyHandler, GameMap map){
        if(harvest){
            stuckInHarvest();
        }else{
            globalMove(keyHandler, map);
        }
    }
    /**
     * handle the movement of the player in fonction of wich key is pressed
     * @param keyHandler
     * @param map
     */
    private void globalMove(KeyHandler keyHandler, GameMap map){
        if(go){
            this.movementToPos();
        }else{
            if(keyHandler.getUp()){
                player.moveUp();
                if(player.isPositionIncorrect(map)){
                    player.moveDown();
                }
                return;
            }
            if(keyHandler.getDown()){
                player.moveDown();
                if(player.isPositionIncorrect(map)){
                    player.moveUp();
                }
                return;
            }
            if(keyHandler.getLeft()){
                player.moveLeft();
                if(player.isPositionIncorrect(map)){
                    player.moveRight();
                }
            }
            if(keyHandler.getRight()){
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
}
