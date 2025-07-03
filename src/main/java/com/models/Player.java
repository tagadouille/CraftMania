package main.java.com.models;

import main.java.com.models.Ressources.Ressource;
import main.java.com.models.Ressources.RessourceEnum;
import main.java.com.models.map.Map;
import main.java.com.views.utilities.KeyHandler;

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
    private boolean isTileIncorrect(Map map, int x, int y){
        if(map.inBound(x, y)){
            return !map.getMap()[y][x].isAccessible();
        }
        return true;
    }
    public boolean isPositionIncorrect(Map map){
        int newX = (int) x;
        int newY = (int) y;
        System.out.println("x : " +  x + " y : " + y);
        return isTileIncorrect(map, newX, newY) || isTileIncorrect(map, newX + 1, newY)
        || isTileIncorrect(map, newX, newY + 1) || isTileIncorrect(map, newX + 1, newY + 1);
    }
    public void globalMove(KeyHandler kh, Map map){

        if(kh.getUp()){
            moveUp();
            if(isPositionIncorrect(map)){
                moveDown();
            }
        }
        if(kh.getDown()){
            moveDown();
            if(isPositionIncorrect(map)){
                moveUp();
            }
            return;
        }
        if(kh.getLeft()){
            moveLeft();
            if(isPositionIncorrect(map)){
                moveRight();
            }
        }
        if(kh.getRight()){
            moveRight();
            if(isPositionIncorrect(map)){
                moveLeft();
            }
            return;
        }
    }
}
