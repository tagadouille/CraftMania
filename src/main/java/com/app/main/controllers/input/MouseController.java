package com.app.main.controllers.input;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import com.app.main.PathFinder;
import com.app.main.controllers.PlayerController;
import com.app.main.controllers.view_controller.props_ui.FactoryViewController;
import com.app.main.controllers.view_controller.props_ui.HarvestViewController;
import com.app.main.controllers.view_controller.props_ui.MarketDialogController;
import com.app.main.models.Player;
import com.app.main.models.machine.Factory;
import com.app.main.models.machine.Harvester;
import com.app.main.models.machine.Machine;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile;
import com.app.main.models.map.Tile.TileType;
import com.app.main.util.design_pattern.Observable;
import com.app.main.util.design_pattern.Observer;
import com.app.main.views.GameView;
import com.app.main.views.props_ui.FactoryView;
import com.app.main.views.props_ui.HarvesterView;
import com.app.main.views.props_ui.MarketDialog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The MouseController class handles mouse input for player interactions with the game map.
 * It processes mouse clicks to determine if the player can interact with tiles such as markets, 
 * resources, harvesters, and factories.
 * It also manages player movement to clicked locations on the map.
 * 
 * @author Dai Elias
 */
public final class MouseController implements Observable{
    
    private GameMap map;
    private Player player;
    private PlayerController playerController;

    private Optional<Machine> machineplacement = Optional.empty();

    private List<Observer> observers = new java.util.ArrayList<>();

    private MouseController(GameMap map, Player player, PlayerController playerController){
        this.map = map;
        this.player = player;
        this.playerController = playerController;
    }

    /**
     * The setMachinePlacement method sets the machine that the player intends to place on the map.
     * @param machine the machine to be placed
     */
    public void setMachinePlacement(Machine machine) {

        if(machine == null) {
            throw new IllegalArgumentException("Machine cannot be null");
        }
        this.machineplacement = Optional.of(machine);
        new Alert(AlertType.INFORMATION, "Click on the map to place the machine").show();
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

        // If the player has selected a machine to place, attempt to place it on the clicked tile
        if(machineplacement.isPresent()) {
            if(clickedTile.isAccessible()) {
                
                Machine machine = machineplacement.get();

                int x = (int) mousePos.getX();
                int y = (int) mousePos.getY();

                boolean good = true;

                if(machine instanceof Harvester) {
                    
                    if(map.getMap()[y + 1][x].getResource().isPresent() || map.getMap()[y - 1][x].getResource().isPresent()
                    || map.getMap()[y][x + 1].getResource().isPresent() || map.getMap()[y][x - 1].getResource().isPresent()) {
                    }
                    else {
                        new Alert(AlertType.ERROR, "You must place the harvester adjacent to a resource").show();
                        good = false;
                    }
                }
                if(good) {
                    observers.get(0).update(this, machine, "" + x + " " + y);
                    machineplacement = Optional.empty();
                }
            }
            return;
        }

        if(player.isAdjacentToTile(mousePos)){
            switch (clickedTile.getType()) {

                case TileType.MARKET :
                    MarketDialog marketDialog = new MarketDialog();
                    MarketDialogController.create(marketDialog, player, this);
                    marketDialog.show();
                    return;
                    
                case TileType.RESOURCETMP :
                case TileType.RESOURCE :
                    playerController.harvest(clickedTile);
                    return;

                case TileType.HARVESTER : 
                    HarvesterView harvesterView = new HarvesterView();
                    HarvestViewController.create(
                        harvesterView, player, map, (int) mousePos.getX(), (int) mousePos.getY()
                    );
                    harvesterView.show();
                    return;
                    
                case TileType.FACTORY :
                    FactoryView factoryView = new FactoryView();
                    FactoryViewController.create((Factory) clickedTile.getMachine().get(), factoryView, player);
                    factoryView.show();
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

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

}
