package com.app.main.models;

/**
 * Abstract class Item representing an item in the game
 * @author Dai Elias
 */
public abstract class Item {
    
    protected String name = "";
    protected final int price;
    protected String typeName = "";

    /**
     * Constructor for Item class.
     */
    public Item(){
        this.price = 0;
    }

    /**
     * Constructor for Item class with price.
     * @param price the price of the item
     */
    public Item(int price){
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    /* Getters and Setters */
    public String getName() {
        return name;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getPrice() {
        return price;
    }

    /**
     * Sets the name of the item.
     * @param name the name to set
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    /**
     * Sets the type name of the item.
     * @param typeName the type name to set
     */
    public void setTypeName(String typeName) {
        if(typeName == null || typeName.isEmpty()){
            throw new IllegalArgumentException("Type name cannot be null or empty.");
        }
        this.typeName = typeName;
    }
}
