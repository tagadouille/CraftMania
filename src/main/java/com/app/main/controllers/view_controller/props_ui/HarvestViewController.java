package com.app.main.controllers.view_controller.props_ui;

import java.util.ArrayList;
import java.util.List;

import com.app.main.models.Player;
import com.app.main.models.machine.Harvester;
import com.app.main.models.map.GameMap;
import com.app.main.models.map.Tile.TileType;
import com.app.main.models.resources.Resource;
import com.app.main.models.resources.ResourceEnum;

import javafx.scene.Node;
import javafx.scene.control.Button;

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
        updateHarvestNb();
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

    private void updateHarvestNb() {
        harvesterView.updateHarvestText(harvester.getNumberOfResource());
    }

    private void harvestNearbyUpdate() {

        List<ResourceEnum> nearbyResource = new ArrayList<>();

        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int i = 0; i < directions.length; i++) {
            
            int newX = x + directions[i][0];
            int newY = y + directions[i][1];

            if(map.inBound(newX, newY)) {

                // Add the resource to the list of nearby resources if the tile is a resource tile
                if(map.getMap()[newY][newX].getType() == TileType.RESOURCE) {
                    nearbyResource.add(
                        ResourceEnum.getResourceEnum(
                            ((Resource) (map.getMap()[newY][newX].getItem())).getName()
                        )
                    );
                }
            }
        }
        harvesterView.updateNearbyResources(nearbyResource);
    }

    private void harvestBehavior() {
        harvestNearbyUpdate();

        harvesterView.getHarvestButton().setOnAction(e -> {
            harvester.cleanInventory(player);
            updateHarvestNb();
        });

        for(Node node : harvesterView.getContent().getChildren()) {

            if(node instanceof Button) {
                Button button = (Button) node;

                button.setOnAction(e -> {
                    ResourceEnum ressourceEnum = ResourceEnum.getResourceEnum(button.getText());

                    if(ressourceEnum != null) {
                        harvester.setProduct(ressourceEnum);
                        harvesterView.getContent().getChildren().clear();
                    }
                });
            }
        }
    }
}
