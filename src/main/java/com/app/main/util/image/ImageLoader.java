package com.app.main.util.image;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;

public final class ImageLoader {
    
    public static Image loadImage(String imagePath) {
        
        try {
            Image image = new Image(Files.newInputStream(Paths.get(imagePath)));
            return image;
        } catch (Exception e) {
            e.printStackTrace();
           return null;
        }
        
    }
}
