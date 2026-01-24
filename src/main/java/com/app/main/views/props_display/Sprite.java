package com.app.main.views.props_display;

import java.io.IOException;

import com.app.main.util.image.ImageUtil;
import com.app.main.views.GameView;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * This class represents a sprite with position, dimensions, and an image.
 * It provides methods to get and set its properties, as well as a method to display the sprite on a graphics context.
 * @author Dai Elias
 */
public class Sprite {
    private int posX, posY;
    private int width, height;
    private Image image;

    protected Sprite(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    /**
     * Factory method to create a Sprite instance.
     * @param posX the X position of the sprite
     * @param posY the Y position of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param image the image of the sprite
     * @return a new Sprite instance
     */
    public static Sprite createSprite(int posX, int posY, int width, int height, Image image){

        if(image == null){
            throw new IllegalArgumentException("Image cannot be null");
        }
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("Width and Height must be positive integers");
        }
        return new Sprite(posX, posY, width, height, image);
    }

    /* Getters and setters : */
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Image getImage() {
        return image;
    }
    
    /**
     * Sets the height of the sprite and resizes the image accordingly.
     * @param height the new height of the sprite
     */
    public void setHeight(int height) {
        if(height <= 0){
            throw new IllegalArgumentException("Height must be a positive integer");
        }
        this.height = height;
        try {
            this.image = ImageUtil.resizeImage(image, height, height);
        }
        catch(IOException e) {
            System.err.println("Error resizing image: " + e.getMessage());
        }
    }

    public void setImage(Image image) {

        if(image == null){
            throw new IllegalArgumentException("Image cannot be null");
        }
        this.image = image;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Sets the width of the sprite and resizes the image accordingly.
     * @param width the new width of the sprite
     */
    public void setWidth(int width) {
        if(height <= 0){
            throw new IllegalArgumentException("Height must be a positive integer");
        }
        this.width = width;
        try {
            this.image = ImageUtil.resizeImage(image, height, height);
        }
        catch(IOException e) {
            System.err.println("Error resizing image: " + e.getMessage());
        }
    }

    /**
     * Display the sprite on the given graphics context.
     * @param g the graphics context to draw the sprite on
     */
    public void display(GraphicsContext g){
        g.drawImage(image, posX * GameView.getMultiplicator(), posY * GameView.getMultiplicator(),
        width * GameView.getMultiplicator(), height * GameView.getMultiplicator());
    }
}
