package com.app.main.models;

public abstract class Item {
    protected String name = "";
    protected final int price;
    protected String typeName = "";

    public Item(){
        this.price = 0;
    }
    public Item(int price){
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public String getTypeName() {
        return typeName;
    }
    public int getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
