package com.app.main.util.image;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.scene.image.Image;

/**
 * Utility class for loading images.
 * @author Dai Elias
 */
public final class ImageLoader {
    
    /**
     * Loads an image from the specified file path.
     * @param imagePath the path to the image file
     * @return the loaded Image object, or null if loading fails
     */
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
