package com.app.main.models.machine;

import com.app.main.models.Inventory;
import com.app.main.models.Item;
import com.app.main.models.Player;
import com.app.main.models.resources.ResourceEnum;

/**
 * Machine class representing a production machine in the game.
 * Extends the Item class and includes production capabilities.
 * 
 * @see Item
 * @author Dai Elias
 */
public sealed abstract class Machine extends Item permits com.app.main.models.machine.Harvester, com.app.main.models.machine.Factory {
    
    protected long production_duration;
    private long production_start;
    
    protected ResourceEnum product = null;
    protected Inventory inventory = new Inventory();

    protected long capacity;

    protected boolean alreadySetted = false;

    /**
     * Constructor for Machine.
     * @param price the price of the machine
     * @param product the resource produced by the machine
     * @param capacity the maximum capacity of the machine's inventory
     * @param production_duration the duration required to produce the resource
     */
    public Machine(int price, long capacity, long production_duration) {
        super(price);

        if(production_duration <= 0) {
            throw new IllegalArgumentException("Production duration must be greater than zero.");
        }
        if(capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.production_duration = production_duration;
        this.capacity = capacity;
        startProduct(product);
    }

    public boolean isAlreadySetted() {
        return alreadySetted;
    }

    public void setProduct(ResourceEnum resource) {
        this.product = resource;
        this.alreadySetted = true;
    }

    /**
     * Returns the number of the resource produced by the machine in its inventory.
     * @return
     */
    public int getNumberOfResource() {
        return this.inventory.countResource(product);
    }

    /**
     * Clear the inventory of the machine by giving the content to the player
     * @param player the player
     */
    public void cleanInventory(Player player) {

        if(!inventory.getInventory().containsKey(product)) {
            return;
        }
        
        inventory.getInventory().get(product).forEach(
            resource -> player.getInventory().addResource(ResourceEnum.getResourceEnum(resource.getName()))
        );
        inventory.getInventory().clear();
    }

    /**
     * Handles the production process of the machine.
     * Checks if the machine can produce the resource based on capacity and production time.
     */
    protected final void product() {

        if(product != null && inventory.countResource(product) <= capacity && 
            production_start + production_duration <= System.currentTimeMillis()) {
            
            inventory.addResource(product);
            production_start = System.currentTimeMillis();
        }
    }

    /**
     * Starts the production of a specified resource.
     * @param product the resource to be produced
     */
    public final void startProduct(ResourceEnum product) {
        this.product = product;
        production_start = System.currentTimeMillis();
    }

    /**
     * Abstract method to be implemented by subclasses for specific processing logic.
     * The process method is called to handle the machine's production cycle.
     */
    public abstract void process();
}
