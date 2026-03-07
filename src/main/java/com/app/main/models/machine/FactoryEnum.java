package com.app.main.models.machine;

/**
 * FactoryEnum is an enumeration that defines the different types of factories available in the game.
 * Each enum value corresponds to a specific factory type 
 * with its own characteristics and production capabilities.
 * 
 * @author Dai Elias
 */
public enum FactoryEnum {
    
    SIMPLE,
    FAST,
    XL,
    WEAK,
    POLY;

    /**
     * Returns the corresponding Factory instance based on the FactoryEnum value.
     * @return a Factory instance corresponding to the enum value
    */
    public Factory getFactory() {
        
        switch (this) {
            case SIMPLE:
                return Factory.createSimpleFactory();
            case FAST:
                return Factory.createFastFactory();
            case XL:
                return Factory.createXLFactory();
            case WEAK:
                return Factory.createWeakFactory();
            
            default:
                return Factory.createPolyFactory();
        }
    }
}
