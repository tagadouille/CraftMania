package com.app.main.models.resources;

/**
 * L'énumération craft contient une liste des crafts du jeu.
 * Ce sont des noms d'objet à fabriquer avec un objet de type recette qui leur est associé
 * 
 * @version 1.3
 * @author Dai Elias
 */
public enum RecipeEnum{

    BRICK(Recipe.create(ResourceEnum.HEAT, ResourceEnum.CLAY, ResourceEnum.BRICK)),

    HAMMER(Recipe.create(ResourceEnum.WOOD, ResourceEnum.QUARTZ, ResourceEnum.HAMMER)),

    DOG(Recipe.create(ResourceEnum.QUARTZ, ResourceEnum.CLAY, ResourceEnum.DOG)),

    CAT(Recipe.create(ResourceEnum.HAMMER, ResourceEnum.QUARTZ, ResourceEnum.CAT)),

    CAR(Recipe.create(ResourceEnum.CAT, ResourceEnum.DOG, ResourceEnum.CAR)),

    TANK(Recipe.create(ResourceEnum.CAR, ResourceEnum.WOOD, ResourceEnum.TANK));

    private Recipe recipe; 

    private RecipeEnum(Recipe recipe){
        this.recipe = recipe;
    }

    /**
     * Getter for the recipe associated with the enum constant.
     * @return the Recipe instance associated with the enum constant.
     */
    public Recipe getRecipe() {
        return recipe;
    }
}
