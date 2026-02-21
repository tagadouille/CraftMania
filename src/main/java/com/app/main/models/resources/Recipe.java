package com.app.main.models.resources;

/**
 * The Recipe class represents a crafting recipe in the game.
 * @author Dai Elias
 */
public class Recipe {

    private final ResourceEnum ingredient1, ingredient2; //The name of the ingredient resources
    private final ResourceEnum result;

    /**
     * Constructor for the Recipe class.
     * @param ingredient1 the name of the first ingredient
     * @param ingredient2 the name of the second ingredient
     * @param result the resulting resource
     * @param timed true if the recipe is timed, false if instant
     */
    public Recipe(ResourceEnum ingredient1, ResourceEnum ingredient2, ResourceEnum result){
        this.ingredient1 = ingredient1;
        this.ingredient2 = ingredient2;
        this.result = result;
    }

    /* Getters */
    
    public ResourceEnum getIngredient1() {
        return ingredient1;
    }
    
    public ResourceEnum getIngredient2() {
        return ingredient2;
    }
    
    public ResourceEnum getResult() {
        return result;
    }
}
