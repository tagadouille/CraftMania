package com.app.main.models.resources;

/**
 * L'énumération craft contient une liste des crafts du jeu.
 * Ce sont des noms d'objet à fabriquer avec un objet de type recette qui leur est associé
 * 
 * @version 1.3
 * @author Dai Elias
 */
public enum RecipeEnum{

    BRICK(new Recipe(ResourceEnum.HEAT, ResourceEnum.CLAY, ResourceEnum.BRICK,true)),

    HAMMER(new Recipe(ResourceEnum.WOOD, ResourceEnum.QUARTZ, ResourceEnum.HAMMER, false)),

    DOG(new Recipe(ResourceEnum.QUARTZ, ResourceEnum.CLAY, ResourceEnum.DOG, false)),

    CAT(new Recipe(ResourceEnum.HAMMER, ResourceEnum.QUARTZ, ResourceEnum.CAT, false)),

    CAR(new Recipe(ResourceEnum.CAT, ResourceEnum.DOG, ResourceEnum.CAR, true)),

    TANK(new Recipe(ResourceEnum.CAR, ResourceEnum.WOOD, ResourceEnum.TANK, true));

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
