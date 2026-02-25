package com.app.main.controllers;

import com.app.main.models.Inventory;
import com.app.main.models.resources.Recipe;

public class Crafter {

    private Inventory playerInventory;

    private Crafter(Inventory playerInventory) {
        this.playerInventory = playerInventory;
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public static Crafter createCrafter(Inventory playerInventory) {
        if(playerInventory == null) {
            throw new IllegalArgumentException("Player inventory cannot be null");
        }
        return new Crafter(playerInventory);
    }

    public void startCrafting(Recipe recipe) {

        if(recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }
        playerInventory.addResource(recipe.getResult());
    }
}
