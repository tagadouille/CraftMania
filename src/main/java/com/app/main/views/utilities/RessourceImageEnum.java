package com.app.main.views.utilities;

public enum RessourceImageEnum {
    
    WOOD("wood"),
    QUARTZ("quartz"),
    CLAY("clay"),
    HEAT("fire");

    private String imagePath;

    private RessourceImageEnum(String imageName){
        this.imagePath = "src/ressources/image/Ressource/" + imageName + ".png";
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
