package main.java.com.views.utilities;

import java.io.File;

import javax.swing.ImageIcon;

public class ImageLoader {
    
    public static ImageIcon loadImage(String imagePath){
        File img = new File(imagePath);

        if(img.exists()){
            return new ImageIcon(imagePath);
        }else{
            System.out.println("Le chemin est invalide : " + imagePath);
            return null;
        }
    }
}
