package com.app.main.controllers;

import java.util.ArrayList;
import java.util.List;

import com.app.main.models.Player;
import com.app.main.models.machine.Machine;
import com.app.main.models.map.GameMap;

/**
 * GameController is a class that manages the main game logic
 * @author Dai Elias
 */
public final class GameController {
    
    private PlayerController playerController;
    private GameMap gameMap;

    private List<Machine> machines = new ArrayList<>();
    
    private GameController(PlayerController playerController, GameMap gameMap) {
        
        this.playerController = playerController;
        this.gameMap = gameMap;
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
    
    /**
     * Proceeds with the game logic
     */
    public void proceed() {
        playerController.process(gameMap);

        for (Machine machine : machines) {
            machine.process();
        }
    }
}
