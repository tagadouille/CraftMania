package com.app.main.models.ressources;

import com.app.main.models.Item;

public class Ressource extends Item implements Cloneable{

    public Ressource(int price){
        super(price);
    }
    public Ressource clone(){
        Ressource ret = new Ressource(price);
        ret.setName(this.name);
        return ret;
    }
}
