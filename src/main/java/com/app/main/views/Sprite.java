/*package main.java.com.views;
import java.awt.Graphics2D;
import java.awt.Image;

import main.java.com.Screen;

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

    public void display(Graphics2D g){
        g.drawImage(image, posX * Screen.getMultiplicator(), posY * Screen.getMultiplicator(),
        width * Screen.getMultiplicator(), height * Screen.getMultiplicator(), null);
    }
}*/
