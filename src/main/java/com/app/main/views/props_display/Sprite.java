package com.app.main.views.props_display;

import com.app.main.views.GameView;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
    private int posX, posY;
    private int width, height;
    private Image image;

    public Sprite(int posX, int posY, int width, int height, Image image){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
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
    
    public void setHeight(int height) {
        this.height = height;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public void display(GraphicsContext g){
        g.drawImage(image, posX * GameView.getMultiplicator(), posY * GameView.getMultiplicator(),
        width * GameView.getMultiplicator(), height * GameView.getMultiplicator());
    }
}
