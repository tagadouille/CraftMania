package com.app.main.views.utilities;

import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * Enum representing different tile images used in the game.
 * Each enum constant is associated with a specific image path.
 */
public enum TileImageEnum implements ImagePath {

    EMPTY(ImagePath.IMAGE_PATH + "grass.png"),
    START(ImagePath.IMAGE_PATH + "start.png"),
    MARKET(ImagePath.IMAGE_PATH + "shop.png");
    
    private Image image;

    private TileImageEnum(String imagePath){
        this.image = ImageLoader.loadImage(imagePath);
    }

    /**
     * Get the image associated with the tile.
     * @return
     */
    public Image getImage() {
        return image;
    }
}
