package com.app.main.models;

import java.awt.Point;

import com.app.main.models.map.GameMap;
import com.app.main.models.resources.Resource;
import com.app.main.models.resources.ResourceEnum;

/**
 * Player class representing the player in the game
 * @author Dai Elias
 */
public class Player {

    private double x, y;
    private int money = 0;
    private Inventory inventory;
    public final static double SPEED = 0.1;

    private Player(int x, int y){
        this.inventory = new Inventory();
        this.x = x;
        this.y = y;
    }

    /**
     * Factory method to create a Player
     * @param x Initial x position
     * @param y Initial y position
     * @return New Player instance
     */
    public static Player createPlayer(int x, int y){

        if(x < 0 || y < 0){
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
        return new Player(x, y);
    }

    /* Getters and setters */
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {

        if(x < 0){
            throw new IllegalArgumentException("X coordinate must be non-negative");
        }
        this.x = x;
    }

    public void setY(double y) {

        if(y < 0){
            throw new IllegalArgumentException("Y coordinate must be non-negative");
        }
        this.y = y;
    }

    public int getMoney() {
        return money;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setMoney(int money) {

        if(money < 0){
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.money = money;
    }

    /**
     * Make the player buy something, if they have enough money
     * @param amount Amount of money to spend
     * @return true if the purchase was successful, false otherwise
     */
    public boolean buy(int amount){

        if(amount < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(money >= amount){
            this.money -= amount;
            return true;
        }
        return false;
    }

    /**
     * Make the player gain money
     * @param amount Amount of money to gain
     */
    public void gain(int amount){
        this.money += amount;
    }

    /**
     * Add a resource to the player's inventory
     * @param resource the resource to add
     */
    public void addResource(ResourceEnum resource){
        this.inventory.addResource(resource);
    }

    /**
     * Remove a resource from the player's inventory
     * @param resource the resource to remove
     * @return the removed resource
     */
    public Resource removeResource(ResourceEnum resource){
        return this.inventory.removeResource(resource);
    }

    /**
     * Move the player up
     */
    public void moveUp(){
        y-=SPEED;

        if(y < 0){
            y = 0;
        }
    }

    /**
     * Move the player down
     */
    public void moveDown(){
        y+=SPEED;

        if(y < 0){
            y = 0;
        }
    }

    /**
     * Move the player left
     */
    public void moveLeft(){
        x-=SPEED;

        if(x < 0){
            x = 0;
        }
    }

    /**
     * Move the player right
     */
    public void moveRight(){
        x+=SPEED;

        if(x < 0){
            x = 0;
        }
    }

    private boolean isTileIncorrect(GameMap map, int x, int y){
        if(map.inBound(x, y)){
            return !map.getMap()[y][x].isAccessible();
        }
        return true;
    }

    /**
     * Inform if the player is at a valid position
     * @param map Map the map
     * @return true if it's the case, false otherwise
     */
    public boolean isPositionIncorrect(GameMap map){

        double left   = x;
        double right  = x + 1;
        double top    = y;
        double bottom = y + 1;

        int leftTile   = (int)Math.floor(left);
        int rightTile  = (int)Math.floor(right - 0.0001);
        int topTile    = (int)Math.floor(top);
        int bottomTile = (int)Math.floor(bottom - 0.0001);

        return isTileIncorrect(map, leftTile,  topTile) ||
            isTileIncorrect(map, rightTile, topTile) ||
            isTileIncorrect(map, leftTile,  bottomTile) ||
            isTileIncorrect(map, rightTile, bottomTile);
    }

    /**
     * Check if the player is adjacent to a tile
     * @param tilePos the position of the tile
     * @return true if it's the case, false otherwise
     */
    public boolean isAdjacentToTile(Point tilePos){
        return (int) tilePos.distance(x, y) <= 1;
    }
}
