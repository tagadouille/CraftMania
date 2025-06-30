package main.java.com.models;

import main.java.com.models.Ressources.Ressource;
import main.java.com.models.Ressources.RessourceEnum;
import main.java.com.views.utilities.KeyHandler;

public class Player {
    private int x, y;
    private int money = 0;
    private Inventory inventory;

    public Player(int x, int y){
        this.inventory = new Inventory();
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
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
        y--;
    }
    public void moveDown(){
        y++;
    }
    public void moveLeft(){
        x--;
    }
    public void moveRight(){
        x++;
    }
    public void globalMove(KeyHandler kh){
        if(kh.getUp()){
            moveUp();
            System.out.println("mmm");
            return;
        }
        if(kh.getDown()){
            moveDown();
            return;
        }
        if(kh.getLeft()){
            moveLeft();
            return;
        }
        if(kh.getRight()){
            moveRight();
            return;
        }
    }
}
