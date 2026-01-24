package com.app.main.controllers.input;

import java.awt.Point;

import com.app.main.PathFinder;
import com.app.main.controllers.PlayerController;
import com.app.main.controllers.view_controller.MarketDialogController;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.map.Tile.TileType;
import com.app.main.views.GameView;
import com.app.main.views.props_ui.MarketDialog;
import com.app.main.models.Market;

/**
 * The MouseController class handles mouse input for player interactions with the game map.
 * It processes mouse clicks to determine if the player can interact with tiles such as markets, 
 * resources, harvesters, and factories.
 * It also manages player movement to clicked locations on the map.
 * 
 * @author Dai Elias
 */
public class MouseController {
    
    private GameMap map;
    private Player player;
    private PlayerController playerController;

    private MouseController(GameMap map, Player player, PlayerController playerController){
        this.map = map;
        this.player = player;
        this.playerController = playerController;
    }

    /**
     * Factory method to create a MouseController instance.
     * @param map the map the player is on
     * @param player the player using the mouse
     * @param playerController the controller managing player actions
     * @return a new MouseController instance
     */
    public static MouseController create(GameMap map, Player player, PlayerController playerController){

        if(map == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        if(player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if(playerController == null) {
            throw new IllegalArgumentException("PlayerController cannot be null");
        }
        return new MouseController(map, player, playerController);
    }
    
    /**
     * Handles mouse click events to interact with the game map.
     * @param mouseX the x-coordinate of the mouse click
     * @param mouseY the y-coordinate of the mouse click
     */
    public void setUp(double mouseX, double mouseY){

        Point mousePos = new Point((int) (mouseX/GameView.getSpriteSize()), (int) (mouseY/GameView.getSpriteSize()));

        Tile clickedTile = map.getMap()[(int) mousePos.getY()][(int) mousePos.getX()];

        if(player.isAdjacentToTile(mousePos)){
            switch (clickedTile.getType()) {

                case TileType.MARKET :
                    MarketDialog marketDialog = new MarketDialog();
                    MarketDialogController.createMarketDialogController(marketDialog, Market.createMarket(player));
                    marketDialog.show();
                    return;

                case TileType.RESOURCE :
                    System.out.println("Récolte");
                    playerController.harvest(clickedTile);
                    return;

                case TileType.HARVESTER : 
                    System.out.println("Ouverture de la récolteuse");
                    return;
                    
                case TileType.FACTORY :
                    System.out.println("Ouverture de l'usine");
                    return;
                
                default:
                    break;
            }
        }
        // Move the player to the clicked position if the tile is accessible
        if(clickedTile.isAccessible()){

            PathFinder.findShortPath(
                map, new int[]{(int) player.getY(), (int) player.getX()},
                new int[]{(int) mousePos.getY(), (int) mousePos.getX()}
            );
            playerController.movementToPos();
        }
    }

}
