package com.app.main.views.utilities;

import com.app.main.util.ImageLoader;

import javafx.scene.image.Image;

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

    public Image getImage() {
        return image;
    }
}
