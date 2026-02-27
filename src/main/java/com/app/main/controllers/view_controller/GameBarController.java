package com.app.main.controllers.view_controller;

import com.app.main.controllers.view_controller.menu.MenuSwitcher;
import com.app.main.controllers.view_controller.props_ui.InventoryViewController;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.save.Saving;
import com.app.main.views.GameBar;
import com.app.main.views.menu.MainMenu;
import com.app.main.views.props_ui.InventoryView;

public class GameBarController {

    private Player player;
    private GameBar gameBar;
    private GameMap gameMap;
    
    private GameBarController(Player player, GameBar gameBar, GameMap gameMap) {

        this.player = player;
        this.gameBar = gameBar;
        this.gameMap = gameMap;

        buttonBehavior();
    }

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
