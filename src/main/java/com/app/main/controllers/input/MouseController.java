package com.app.main.controllers.input;

import java.awt.Point;

import com.app.main.PathFinder;
import com.app.main.controllers.PlayerController;
import com.app.main.controllers.view_controller.MarketDialogController;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.views.GameView;
import com.app.main.views.props_ui.MarketDialog;
import com.app.main.models.map.TileType;
import com.app.main.models.Market;

public class MouseController {
    
    private GameMap map;
    private Player player;
    private PlayerController playerController;

    public MouseController(GameMap map, Player player, PlayerController playerController){
        this.map = map;
        this.player = player;
        this.playerController = playerController;
    }
    
    public void setUp(double mouseX, double mouseY){

        Point mousePos = new Point((int) (mouseX/GameView.getSpriteSize()), (int) (mouseY/GameView.getSpriteSize()));

        Tile clickedTile = map.getMap()[(int) mousePos.getY()][(int) mousePos.getX()];

        if(player.isAdjacentToTile(mousePos)){
            switch (clickedTile.getType()) {

                case TileType.MARKET :
                    MarketDialog marketDialog = new MarketDialog();
                    new MarketDialogController(marketDialog, new Market(player));
                    marketDialog.show();
                    return;

                case TileType.RESSOURCE :
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

        if(clickedTile.isAccessible()){

            PathFinder.findShortPath(
                map, new int[]{(int) player.getY(), (int) player.getX()},
                new int[]{(int) mousePos.getY(), (int) mousePos.getX()}
            );
            playerController.movementToPos();
        }
    }

}
