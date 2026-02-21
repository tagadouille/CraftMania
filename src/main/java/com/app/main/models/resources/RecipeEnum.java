package com.app.main.models.resources;

/**
 * L'énumération craft contient une liste des crafts du jeu.
 * Ce sont des noms d'objet à fabriquer avec un objet de type recette qui leur est associé
 * 
 * @version 1.3
 * @author Dai Elias
 */
public enum RecipeEnum{

    BRICK(new Recipe(ResourceEnum.HEAT, ResourceEnum.CLAY, ResourceEnum.BRICK)),

    HAMMER(new Recipe(ResourceEnum.WOOD, ResourceEnum.QUARTZ, ResourceEnum.HAMMER)),

    DOG(new Recipe(ResourceEnum.QUARTZ, ResourceEnum.CLAY, ResourceEnum.DOG)),

    CAT(new Recipe(ResourceEnum.HAMMER, ResourceEnum.QUARTZ, ResourceEnum.CAT)),

    CAR(new Recipe(ResourceEnum.CAT, ResourceEnum.DOG, ResourceEnum.CAR)),

    TANK(new Recipe(ResourceEnum.CAR, ResourceEnum.WOOD, ResourceEnum.TANK));

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
