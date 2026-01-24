package com.app.main.models.resources;

import com.app.main.models.Item;

/**
 * Class representing a resource in the game.
 * Extends the Item class and implements Cloneable interface.
 * @see Item
 * @see Cloneable
 * @author Dai Elias
 */
public final class Resource extends Item implements Cloneable{

    /**
     * Constructor for Ressource class.
     * @param price the price of the resource
     */
    public Resource(int price){
        super(price);
    }

    @Override
    public Resource clone(){
        Resource ret = new Resource(price);
        ret.setName(this.name);
        return ret;
    }
}
