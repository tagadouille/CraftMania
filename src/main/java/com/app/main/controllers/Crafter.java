package com.app.main.controllers;

import com.app.main.models.Inventory;
import com.app.main.models.resources.Recipe;

/**
 * The Crafter class is responsible for handling the crafting logic in the application. 
 * It interacts with the player's inventory to manage the crafting process
 * ased on defined recipes.
 * 
 * @see Inventory
 * @see Recipe
 * 
 * @author Dai Elias
 */
public class Crafter {

    private Inventory playerInventory;

    private Crafter(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    /**
     * Creates a new instance of the Crafter class with the provided player inventory.
     * @param playerInventory The inventory of the player that will be used for crafting operations.
     * @return A new instance of the Crafter class.
     * @throws IllegalArgumentException if the player inventory is null.
     */
    public static Crafter createCrafter(Inventory playerInventory) {
        if(playerInventory == null) {
            throw new IllegalArgumentException("Player inventory cannot be null");
        }
        return new Crafter(playerInventory);
    }

    /**
     * Performs the crafting operation based on the provided recipe. It updates the player's inventory
     * by adding the crafted item and removing the required ingredients.
     * @param recipe The recipe that defines the crafting operation to be performed.
     * @throws IllegalArgumentException if the recipe is null.
     */
    public void crafting(Recipe recipe) {

        if(recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        playerInventory.addResource(recipe.getResult());
        playerInventory.removeResource(recipe.getIngredient1());
        playerInventory.removeResource(recipe.getIngredient2());
    }
}
