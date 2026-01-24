package com.app.main.views.utilities;

import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * Enum representing different item images used in the application.
 * Each enum constant is associated with a specific image loaded from resources.
 * @author Dai Elias
 */
public enum ItemImageEnum {
    WOOD("wood"),
    QUARTZ("quartz"),
    CLAY("clay"),
    HEAT("fire"),
    BRICK("brick"),
    CAR("car"),
    CAT("cat"),
    TANK("tank"),
    HAMMER("hammer"),
    DOG("dog");

    private Image image;

    private ItemImageEnum(String imageName){
        this.image = ImageLoader.loadImage("src/main/resources/com/app/image/item/" + imageName + ".png");
    }

    /**
     * Get the image associated with the item.
     * @return
     */
    public Image getImage() {
        return image;
    }
}
