package com.app.main.views.utilities;

import com.app.main.models.machine.Harvester;
import com.app.main.util.image.ImageLoader;

import javafx.scene.image.Image;

/**
 * 
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
     * 
     * @param harvester
     * @return
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
}
