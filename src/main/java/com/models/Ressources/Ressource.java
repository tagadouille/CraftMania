package main.java.com.models.Ressources;

import main.java.com.models.map.Item;

public class Ressource extends Item{
    private final int price;

    public Ressource(int price){
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
}
