package com.app.main.views.utilities;

public enum TileImageEnum {

    EMPTY("src/ressources/image/grass.png"),
    START("src/ressources/image/start.png"),
    MARKET("src/ressources/image/shop.png");
    
    private String imagePath;

    private TileImageEnum(String imagePath){
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
