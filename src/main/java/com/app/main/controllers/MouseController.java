package com.app.main.controllers;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.app.main.PathFinder;
import com.app.main.models.Player;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;

public class MouseController {
    
    private GameMap map;
    private Player player;
    private PlayerController playerController;

    public MouseController(GameMap map, Player player, PlayerController playerController){
        this.map = map;
        this.player = player;
        this.playerController = playerController;
    }
    public void process(MouseEvent e){
        //Point mousePos = new Point(e.getX()/Screen.getSpriteSize(), e.getY()/Screen.getSpriteSize());

        //Tile clickedTile = map.getMap()[(int) mousePos.getY()][(int) mousePos.getX()];

        /*if(player.isAdjacentToTile(mousePos)){
            switch (clickedTile.getType()) {
                case TileType.MARKET :
                    MarketDialog marketDialog = new MarketDialog(new Market(player));
                    marketDialog.setVisible(true);
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
            }
        }
        if(clickedTile.isAccessible()){
            PathFinder.findShortPath(map, new int[]{(int) player.getY(), (int) player.getX()},
             new int[]{(int) mousePos.getY(), (int) mousePos.getX()});
            playerController.movementToPos();
        }*/
    }

}
