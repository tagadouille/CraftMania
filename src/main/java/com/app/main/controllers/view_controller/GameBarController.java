package com.app.main.controllers.view_controller;

import com.app.main.controllers.view_controller.menu.MenuSwitcher;
import com.app.main.controllers.view_controller.props_ui.InventoryViewController;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.save.Saving;
import com.app.main.views.GameBar;
import com.app.main.views.menu.MainMenu;
import com.app.main.views.props_ui.InventoryView;

/**
 * The GameBarController class manages the interactions and behaviors of the GameBar view,
 * which includes handling button actions for quitting the game, 
 * saving the game, and accessing the inventory.
 * 
 * @see GameBar
 * @see MainMenu
 * 
 * @author Dai Elias
 */
public final class GameBarController {

    private Player player;
    private GameBar gameBar;
    private GameMap gameMap;
    
    private GameBarController(Player player, GameBar gameBar, GameMap gameMap) {

        this.player = player;
        this.gameBar = gameBar;
        this.gameMap = gameMap;

        buttonBehavior();
    }

    /**
     * Factory method to create a GameBarController instance
     *  with the specified player, game bar, and game map.
     * @param player the player of the game
     * @param gameBar the game bar to be controlled
     * @param gameMap the game map to be used for saving the game state
     * @return a new GameBarController instance with the specified player, game bar, and game map
     * @throws IllegalArgumentException if any of the parameters are null
    */
    public static GameBarController create(Player player, GameBar gameBar, GameMap gameMap) {

        if(player == null) {
            throw new IllegalArgumentException("The player can't be null");
        }

        if(gameBar == null) {
            throw new IllegalArgumentException("The gamebar can't be null");
        }
        return new GameBarController(player, gameBar, gameMap);
    }

    private void buttonBehavior() {

        gameBar.getQuit().setOnAction((e) -> {
            MenuSwitcher.switchScene(MainMenu.create(480, 480));
        });

        gameBar.getInventory().setOnAction((e) -> {
            InventoryView inventoryView = new InventoryView();
            InventoryViewController.create(inventoryView, player.getInventory());
            inventoryView.show();
        });

        gameBar.getSave().setOnAction((e) -> {
            gameBar.saveNotif(Saving.save(player, gameMap));
        });
    }
}
