package com.app.main.views.utilities;

import com.app.main.models.machine.Factory;
import com.app.main.models.machine.FactoryEnum;
import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * The FactoryImageEnum enum is used to associate each type of factory with its corresponding image.
 * It provides methods to retrieve the image for a given factory or factory enum.
 * 
 * @author Dai Elias
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
     * transform a factory into its corresponding image
     * @param factory the factory to transform
     * @return the image corresponding to the factory
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

    /**
     * transform a factory enum into its corresponding image
     * @param factory the factory enum to transform
     * @return the image corresponding to the factory enum
     */
    public static Image factoryToImage(FactoryEnum factory) {

        switch (factory) {
            case FactoryEnum.FAST:
                return FAST.getImage();

            case FactoryEnum.SIMPLE:
                return BASIC.getImage();

            case FactoryEnum.XL:
                return XL.getImage();

            case FactoryEnum.WEAK:
                return WEAK.getImage();

            default:
                return POLY.getImage();
        }
    }
    
}
