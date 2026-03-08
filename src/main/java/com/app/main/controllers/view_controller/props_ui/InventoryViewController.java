package com.app.main.controllers.view_controller.props_ui;

import java.util.ArrayList;
import java.util.List;

import com.app.main.controllers.Crafter;
import com.app.main.models.Inventory;
import com.app.main.views.props_ui.InventoryView;
import com.app.main.models.resources.RecipeEnum;
import com.app.main.models.resources.ResourceEnum;

import javafx.scene.layout.VBox;
import javafx.scene.Node;

/**
 * The InventoryViewController class is responsible for managing the interactions between the InventoryView
 *  and the underlying data models, such as the player's inventory and crafting system.
 * It handles user input from the InventoryView and updates the view accordingly based on changes in the inventory or crafting results.
 * 
 * @see InventoryView
 * @see Crafter
 * @author Dai Elias
 */
public final class InventoryViewController {
    
    private InventoryView inventoryView;

    private Crafter crafter;

    private InventoryViewController(InventoryView inventoryView, Inventory playerInventory){
        this.inventoryView = inventoryView;
        craftingBehavior();
        crafter = Crafter.createCrafter(playerInventory);
        updateItemNb();
    }

    public static InventoryViewController create(InventoryView inventoryView, Inventory playerInventory) {

        if(inventoryView == null) {
            throw new IllegalArgumentException("The inventory view can't be null");
        }
        return new InventoryViewController(inventoryView, playerInventory);
    }

    private void updateItemNb() {

        for (ResourceEnum res : new ArrayList<>(crafter.getPlayerInventory().getInventory().keySet())) {
            inventoryView.getItemNb()[res.ordinal()].setText(crafter.getPlayerInventory().countResource(res) + "");
        }
    }

    private void craftingBehavior() {
        
        List<VBox> lines = inventoryView.getCraftPane().getLines();

        int i = 0;

        for(VBox line : lines) {
            
            for(Node box : line.getChildren()) {

                int finalI = i;

                box.setOnMouseClicked(e -> {
                    RecipeEnum recipe = RecipeEnum.values()[finalI];

                    crafter.crafting(recipe.getRecipe());
                    updateItemNb();
                });
                
                i++;
            }
        }
        
    }
}
