package com.app.main.views.utilities;

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

    private String imagePath;

    private ItemImageEnum(String imageName){
        this.imagePath = "src/main/ressources/com/app/image/item/" + imageName + ".png";
    }

    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
