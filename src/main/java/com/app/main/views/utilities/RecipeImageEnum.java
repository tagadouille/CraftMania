package com.app.main.views.utilities;

import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * Enum representing different craft item images used in the application.
 * Each enum constant is associated with a specific image loaded from resources.
 * 
 * @author Dai Elias
 */
public enum RecipeImageEnum implements ImagePath {

    BRICK("brick"),
    CAR("car"),
    CAT("cat"),
    TANK("tank"),
    HAMMER("hammer"),
    DOG("dog");

    private Image image;

    private RecipeImageEnum(String imageName){
        this.image = ImageLoader.loadImage( ImagePath.IMAGE_PATH + "item/" + imageName + ".png");
    }

    /**
     * Get the image associated with the craft.
     * @return
     */
    public Image getImage() {
        return image;
    }
    
}
