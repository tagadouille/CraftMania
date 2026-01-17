package com.app.main.views.utilities;

public enum TileImageEnum {

    EMPTY("src/main/ressources/com/app/image/grass.png"),
    START("src/main/ressources/com/app/image/start.png"),
    MARKET("src/main/ressources/com/app/image/shop.png");
    
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
