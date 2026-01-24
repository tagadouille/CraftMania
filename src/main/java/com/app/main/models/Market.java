package com.app.main.models;

import com.app.main.models.resources.ResourceEnum;

/**
 * Market class representing a marketplace where players can buy and sell items.
 * It interacts with the Player class to manage transactions.
 * @author Dai Elias
 */
public class Market {
    
    private Player player;

    private Market(Player player){
        this.player = player;
    }

    /**
     * Factory method to create a Market instance for a given player.
     * @param player the player associated with the market
     * @return a new Market instance
     */
    public static Market createMarket(Player player){

        if(player == null){
            throw new IllegalArgumentException("Player cannot be null");
        }
        return new Market(player);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Method for a player to buy an item from the market.
     * @param item the item to be purchased
     */
    public void buy(Item item){
        this.player.buy(item.price);

        if(item.getTypeName().equals("resource")){
            this.player.addResource(ResourceEnum.getResourceEnum(item.getName()));
        }else{
            //TODO Placer l'usine et déterminer si on peut la placer
        }
    }

    /**
     * Method for a player to sell an item to the market.
     * @param item the item to be sold
     */
    public void sellResource(Item item){
        this.player.gain(item.price);
        this.player.removeResource(ResourceEnum.getResourceEnum(item.getName()));
    }
}
