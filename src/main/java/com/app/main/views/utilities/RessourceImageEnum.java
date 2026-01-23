package com.app.main.views.utilities;

import com.app.main.util.ImageLoader;

import javafx.scene.image.Image;

public enum RessourceImageEnum {
    
    WOOD("wood"),
    QUARTZ("quartz"),
    CLAY("clay"),
    HEAT("fire");

    private Image image;

    private RessourceImageEnum(String imageName){
        this.image = ImageLoader.loadImage("src/main/resources/com/app/image/Ressource/" + imageName + ".png");
    }

    public Image getImage() {
        return image;
    }
}
