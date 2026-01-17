package com.app.main.controllers;

import com.app.main.models.Player;
import com.app.main.models.map.GameMap;

public class GameController {
    
    private PlayerController playerController;
    private GameMap gameMap;
    
    public GameController(PlayerController playerController, GameMap gameMap) {
        
        this.playerController = playerController;
        this.gameMap = gameMap;
    }

    public Player getPlayer() {
        return this.playerController.getPlayer();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void proceed() {
        // TODO
    }
}
