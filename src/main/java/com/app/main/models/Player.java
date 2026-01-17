package com.app.main.models;

import java.awt.Point;

import com.app.main.models.map.GameMap;
import com.app.main.models.ressources.Ressource;
import com.app.main.models.ressources.RessourceEnum;

public class Player {
    private double x, y;
    private int money = 0;
    private Inventory inventory;
    public final static double SPEED = 0.1;

    public Player(int x, int y){
        this.inventory = new Inventory();
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getMoney() {
        return money;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public boolean buy(int amount){
        if(money >= amount){
            this.money -= amount;
            return true;
        }
        return false;
    }
    public void gain(int amount){
        this.money += amount;
    }
    public void addRessource(RessourceEnum ressource){
        this.inventory.addRessource(ressource);
    }
    public Ressource removeRessource(RessourceEnum ressource){
        return this.inventory.removeRessource(ressource);
    }
    public int numberRessourceInInventory(RessourceEnum ressource){
        if(!this.inventory.getInventory().containsKey(ressource)){
            return 0;
        }
        return this.inventory.getInventory().get(ressource).size();
    }
    public void moveUp(){
        y-=SPEED;
    }
    public void moveDown(){
        y+=SPEED;
    }
    public void moveLeft(){
        x-=SPEED;
    }
    public void moveRight(){
        x+=SPEED;
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
        int newX = (int) x;
        int newY = (int) y;
        //System.out.println("x : " +  x + " y : " + y);
        return x <= 0 || y <= 0 || isTileIncorrect(map, newX, newY) || isTileIncorrect(map, newX + 1, newY)
        || isTileIncorrect(map, newX, newY + 1) || isTileIncorrect(map, newX + 1, newY + 1);
    }
    public boolean isAdjacentToTile(Point tilePos){
        return (int) tilePos.distance(x, y) <= 1;
    }
}
