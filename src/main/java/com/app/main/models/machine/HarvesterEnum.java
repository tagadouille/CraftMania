package com.app.main.models.machine;

/**
 * HarvesterEnum is an enumeration that defines 
 * the different types of harvesters available in the game.
 * Each enum value corresponds to a specific harvester type
 *  with its own characteristics and harvesting capabilities.
 * 
 * @author Dai Elias
 */
public enum HarvesterEnum {
    
    SIMPLE,
    FAST,
    XL,
    WEAK,
    POLY;

    /**
     * Returns the corresponding Harvester instance based on the HarvesterEnum value.
     * @return a Harvester instance corresponding to the enum value
     */
    public Harvester getHarvester() {
        
        switch (this) {
            case SIMPLE:
                return Harvester.createSimpleHarvester();
            case FAST:
                return Harvester.createFastHarvester();
            case XL:
                return Harvester.createXLHarvester();
            case WEAK:
                return Harvester.createWeakHarvester();
            
            default:
                return Harvester.createPolyHarvester();
        }
    }
}
