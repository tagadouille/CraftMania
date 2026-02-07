package com.app.main.controllers.view_controller.props_ui;

import java.util.ArrayList;
import java.util.List;

import com.app.main.models.Player;
import com.app.main.models.machine.Harvester;
import com.app.main.models.map.GameMap;
import com.app.main.models.resources.ResourceEnum;

/**
 * The HarvestViewController class is responsible for managing the interactions between the HarvesterView
 *  and the underlying data models, such as the game map and nearby resources.
 * 
 * @author Dai Elias
 */
public class HarvestViewController {
    
    private HarvesterView harvesterView;
    private Harvester harvester;
    private Player player;

    private int x,y;

    private GameMap map;

    private HarvestViewController(HarvesterView harvesterView, Player player, GameMap map, int x, int y) {
        this.harvesterView = harvesterView;

        this.x = x;
        this.y = y;

        this.map = map;
        this.harvester = (Harvester) map.getMap()[y][x].getItem();
        this.player = player;
        harvestBehavior();
    }

    /**
     * Factory method to create an instance of HarvestViewController with necessary validations.
     * @param harvesterView the harvest view
     * @param player the player
     * @param map the map of the game
     * @param x the x coordinate of the harvester on the map
     * @param y the y coordinate of the harvester on the map
     * @return a new HarvestViewController instance
     */
    public static HarvestViewController create(HarvesterView harvesterView, Player player, GameMap map, int x, int y) {
        if(harvesterView == null) {
            throw new IllegalArgumentException("Harvester view cannot be null");
        }
        if(map == null) {
            throw new IllegalArgumentException("Game map cannot be null");
        }
        if(!map.inBound(x, y)) {
            throw new IllegalArgumentException("Coordinates are out of bounds");
        }
        return new HarvestViewController(harvesterView, player, map, x, y);
    }

    private void harvestBehavior() {

        harvesterView.getHarvestButton().setOnAction(e -> {
            harvester.cleanInventory(player);
        });

        List<ResourceEnum> nearbyResource = new ArrayList<>();
        nearbyResource.forEach(resource -> {
            if(map.getMap()[y][x].getItem().) {
                nearbyResource.add(resource);
            }
        });
        harvesterView.updateNearbyResources(null);
    }
}
