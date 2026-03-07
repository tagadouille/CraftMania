package com.app.main.views.utilities;

import com.app.main.models.machine.Harvester;
import com.app.main.models.machine.HarvesterEnum;
import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * The HarvesterImageEnum enum is used to associate each type of harvester with its corresponding image.
 * It provides methods to retrieve the image for a given harvester or harvester enum.
 * 
 * @author Dai Elias
 */
public enum HarvesterImageEnum implements ImagePath {

    BASIC(""), 
    FAST("Fast"),
    XL("XL"),
    POLY("Poly"),
    WEAK("Weak");

    private Image image;

    private HarvesterImageEnum(String imageName){
        this.image = ImageLoader.loadImage( ImagePath.IMAGE_PATH + "harvester/harvester" + imageName + ".png");
    }

    /**
     * Get the image associated with the item.
     * @return
     */
    public Image getImage() {
        return image;
    }

    /**
     * The harvesterToImage method transforms a harvester into its corresponding image.
     * @param harvester the harvester to transform
     * @return the image corresponding to the harvester
     */
    public static Image harvesterToImage(Harvester harvester) {

        if(harvester instanceof Harvester.FastHarvester) {
            return FAST.getImage();
        }
        else if(harvester instanceof Harvester.SimpleHarvester) {
            return BASIC.getImage();
        }
        else if(harvester instanceof Harvester.XLHarvester) {
            return XL.getImage();
        }
        else if(harvester instanceof Harvester.WeakHarvester) {
            return WEAK.getImage();
        }
        return POLY.getImage();
    }

    /**
     * The harvesterToImage method transforms a harvester enum into its corresponding image.
     * @param harvester the harvester enum to transform
     * @return the image corresponding to the harvester enum
     */
    public static Image harvesterToImage(HarvesterEnum harvester) {

        switch (harvester) {
            case HarvesterEnum.FAST:
                return FAST.getImage();
            case HarvesterEnum.SIMPLE:
                return BASIC.getImage();
            case HarvesterEnum.XL:
                return XL.getImage();
            case HarvesterEnum.WEAK:
                return WEAK.getImage();
            default:
                break;
        }
        return POLY.getImage();
    }
}
