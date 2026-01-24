package com.app.main.views.utilities;

import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * The ResourceImageEnum enum represents different resource images used in the application.
 * Each enum constant is associated with a specific image loaded from the resources.
 */
public enum ResourceImageEnum {
    
    WOOD("wood"),
    QUARTZ("quartz"),
    CLAY("clay"),
    HEAT("fire");

    private Image image;

    private ResourceImageEnum(String imageName){
        this.image = ImageLoader.loadImage("src/main/resources/com/app/image/Ressource/" + imageName + ".png");
    }

    /**
     * Get the image associated with the resource.
     * @return
     */
    public Image getImage() {
        return image;
    }
}
