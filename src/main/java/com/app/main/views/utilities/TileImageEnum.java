package com.app.main.views.utilities;

import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

public enum TileImageEnum {

    EMPTY("src/main/resources/com/app/image/grass.png"),
    START("src/main/resources/com/app/image/start.png"),
    MARKET("src/main/resources/com/app/image/shop.png");
    
    private Image image;

    private TileImageEnum(String imagePath){
        this.image = ImageLoader.loadImage(imagePath);
    }
    public Image getImage() {
        return image;
    }
}
