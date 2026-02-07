package com.app.main.views.utilities;

import com.app.main.models.machine.Factory;
import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * 
 */
public enum FactoryImageEnum {

    BASIC(""), 
    FAST("Fast"),
    XL("XL"),
    POLY("Poly"),
    WEAK("Weak");

    private Image image;

    private FactoryImageEnum(String imageName){
        this.image = ImageLoader.loadImage( ImagePath.IMAGE_PATH + "factory/factory" + imageName + ".png");
    }

    /**
     * Get the image associated with the item.
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * 
     * @param factory
     * @return
     */
    public static Image factoryToImage(Factory factory) {

        if(factory instanceof Factory.FastFactory) {
            return FAST.getImage();
        }
        else if(factory instanceof Factory.SimpleFactory) {
            return BASIC.getImage();
        }
        else if(factory instanceof Factory.XLFactory) {
            return XL.getImage();
        }
        else if(factory instanceof Factory.WeakFactory) {
            return WEAK.getImage();
        }
        return POLY.getImage();
    }
    
}
