package com.app.main.models.resources;

import com.app.main.models.Player;

/**
 * Class representing a temporary resource in the game.
 * Extends the Resource class and is final.
 * 
 * @see Resource
 * @author Dai Elias
 */
public final class ResourceTMP extends Resource {

    public static final long RESPAWN_TIME = 5000;
    private long lastRespawnTime = 0;
    private boolean isRespawned = true;

    /**
     * Constructor for ResourceTMP class.
     * @param price the price of the resource
     */
    public ResourceTMP(int price) {
        super(price);
    }

    public boolean isRespawned() {
        return isRespawned;
    }

    /**
     * The resource is added to player inventory, setting it to not respawned 
     * and updating the last respawn time.
     * @param player the player who picks the resource
     */
    public void pick(Player player) {
        if (isRespawned) {
            isRespawned = false;
            lastRespawnTime = System.currentTimeMillis();
            player.addResource(ResourceEnum.getResourceEnum(this.getName()));
        }
    }

    /**
     * Processes the respawn logic, checking if the resource can be respawned 
     * based on the last respawn time and the defined respawn time.
     */
    public void processRespawn() {
        if (!isRespawned) {
            if (System.currentTimeMillis() - lastRespawnTime >= RESPAWN_TIME) {
                isRespawned = true;
            }
        }
    }
     
}
