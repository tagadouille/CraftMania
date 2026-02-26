package com.app.main.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.app.main.models.Player;
import com.app.main.models.machine.Machine;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.resources.ResourceTMP;

/**
 * GameController is a class that manages the main game logic
 * @author Dai Elias
 */
public final class GameController { //TODO OBSERVER, SINGLETON
    
    private PlayerController playerController;
    private GameMap gameMap;

    private List<Machine> machines = new ArrayList<>();
    private List<ResourceTMP> resourcesTMP = new ArrayList<>();
    
    private GameController(PlayerController playerController, GameMap gameMap) {
        
        this.playerController = playerController;
        this.gameMap = gameMap;
        machines.add((Machine) gameMap.getMap()[2][2].getItem());
        addItemToLists();
    }

    /**
     * Factory method to create a GameController instance
     * @param playerController the PlayerController instance
     * @param gameMap the GameMap instance
     * @return a new GameController instance
     */
    public static GameController createGameController(PlayerController playerController, GameMap gameMap) {

        if(playerController == null){
            throw new IllegalArgumentException("PlayerController cannot be null");
        }
        if(gameMap == null){
            throw new IllegalArgumentException("GameMap cannot be null");
        }
        return new GameController(playerController, gameMap);
    }

    /* Getters : */

    public Player getPlayer() {
        return this.playerController.getPlayer();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public List<ResourceTMP> getResourcesTMP() {
        return resourcesTMP;
    }

    private void addItemToLists() {

        for (int i = 0; i < gameMap.getHeight(); i++) {
            for (int j = 0; j < gameMap.getWidth(); j++) {
                
                Tile tile = gameMap.getMap()[i][j];

                if(tile.getMachine() != null){
                    if(Optional.ofNullable(tile.getMachine()).isPresent()){
                        machines.add(tile.getMachine().get());
                    }
                }
                else if(tile.getResource().isPresent() && tile.getResource().get() instanceof ResourceTMP){
                    resourcesTMP.add((ResourceTMP) tile.getResource().get());
                }
            }
        }
    }
    
    /**
     * Proceeds with the game logic
     */
    public void proceed() {
        playerController.process(gameMap);

        for (Machine machine : machines) {
            machine.process();
        }

        for (ResourceTMP resource : resourcesTMP) {
            resource.processRespawn();
        }
    }
}
