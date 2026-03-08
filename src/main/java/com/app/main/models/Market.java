package com.app.main.models;

import com.app.main.models.machine.FactoryEnum;
import com.app.main.models.machine.HarvesterEnum;
import com.app.main.models.resources.ResourceEnum;

/**
 * Market class representing a marketplace where players can buy and sell items.
 * It interacts with the Player class to manage transactions.
 * @author Dai Elias
 */
public final class Market {
    
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
     * @param resource the resource to be bought
     */
    public void buyResource(ResourceEnum resource) {

        if(player.getMoney() < resource.getResource().price) {
            return;
        }
        this.player.buy(resource.getResource().price);
        this.player.addResource(resource);
    }

    /**
     * Method for a player to buy a factory from the market.
     * @param factory the factory to be bought
     */
    public void buyFactory(FactoryEnum factory) {

        if(player.getMoney() < factory.getFactory().getPrice()) {
            return;
        }
        this.player.buy(factory.getFactory().getPrice());
    }

    /**
     * Method for a player to buy a harvester from the market.
     * @param harvester the harvester to be bought
     */
    public void buyHarvester(HarvesterEnum harvester) {

        if(player.getMoney() < harvester.getHarvester().getPrice()) {
            return;
        }
        this.player.buy(harvester.getHarvester().getPrice());
    }

    /**
     * Method for a player to sell an item to the market.
     * @param resource the resource to be sold
     */
    public void sellResource(ResourceEnum resource){

        if(player.getInventory().countResource(resource) == 0) {
            return;
        }
        this.player.gain(resource.getResource().price);
        this.player.removeResource(resource);
    }
}
