package com.app.main.models.machine;

import java.util.Random;

import com.app.main.models.resources.ResourceEnum;

/**
 * Harvester class representing a type of machine that harvests resources.
 * This class is sealed and can only be extended by specific harvester types.
 * Extends the Machine class.
 * @see Machine
 * @author Dai Elias
 */
public sealed abstract class Harvester extends Machine permits com.app.main.models.machine.Harvester.SimpleHarvester, com.app.main.models.machine.Harvester.FastHarvester, com.app.main.models.machine.Harvester.WeakHarvester, com.app.main.models.machine.Harvester.PolyHarvester, com.app.main.models.machine.Harvester.XLHarvester{

    /**
     * Constructor for Harvester class.
     * @param price the price of the harvester
     * @param product the resource produced by the harvester
     * @param capacity the capacity of the harvester
     * @param production_duration the duration of production cycle
     */
    public Harvester(int price, ResourceEnum product, long capacity, long production_duration) {
        super(price, product, capacity, production_duration);
        this.typeName = "harvester";
    }

    /**
     * Constructor for Harvester class.
     * @param price the price of the harvester
     * @param product the resource produced by the harvester
     */
    public Harvester(int price, ResourceEnum product) {
        this(price, product, 100,5000);
    }

    /**
     * Constructor for Harvester class.
     * @param price the price of the harvester
     * @param product the resource produced by the harvester
     * @param capacity the capacity of the harvester
     */
    public Harvester(int price, ResourceEnum product, long capacity){
        this(price, product, capacity, 5000);
    }

    @Override
    public void process() {
        product();
    }
    
    /**
     * Factory method to create a SimpleHarvester.
     * @param product the resource produced by the harvester
     * @return a new SimpleHarvester instance
     */
    public static SimpleHarvester createSimpleHarvester(ResourceEnum product) {
        return new SimpleHarvester(product);
    }

    /**
     * Factory method to create a FastHarvester.
     * @param product the resource produced by the harvester
     * @return a new FastHarvester instance
     */
    public static FastHarvester createFastHarvester(ResourceEnum product) {
        return new FastHarvester(product);
    }

    /**
     * Factory method to create a XLHarvester.
     * @param product the resource produced by the harvester
     * @return a new XLHarvester instance
     */
    public static XLHarvester createXLHarvester(ResourceEnum product) {
        return new XLHarvester(product);
    }

    /**
     * Factory method to create a WeakHarvester.
     * @param product the resource produced by the harvester
     * @return a new WeakHarvester instance
     */
    public static WeakHarvester createWeakHarvester(ResourceEnum product) {
        return new WeakHarvester(product);
    }

    /**
     * Factory method to create a PolyHarvester.
     * @param product the resource produced by the harvester
     * @return a new PolyHarvester instance
     */
    public static PolyHarvester createPolyHarvester(ResourceEnum product) {
        return new PolyHarvester(product);
    }

    /**
     * SimpleHarvester class representing a basic harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class SimpleHarvester extends Harvester {

        public SimpleHarvester(ResourceEnum product) {

            super(100, product);
        }
    }

    /**
     * SimpleHarvester class representing a fast harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class FastHarvester extends Harvester {
        
        public FastHarvester(ResourceEnum product) {
            super(250, product, 150, 2500);
        }
    }

    /**
     * SimpleHarvester class representing a large capacity harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class XLHarvester extends Harvester {
        
        public XLHarvester(ResourceEnum product) {
            super(175, product, 350);
        }
    }

    /**
     * SimpleHarvester class representing a polyvalent harvester that can be configured multiple times.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class PolyHarvester extends Harvester {

        public PolyHarvester(ResourceEnum product) {
            super(150, product);
        }
    }

    /**
     * SimpleHarvester class representing a weak harvester that can break down.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class WeakHarvester extends Harvester{

        private boolean broken = false;
        
        public WeakHarvester(ResourceEnum product) {
            super(50, product);
        }

        @Override
        /**
         * Processes the harvester, with a chance to break down.
         */
        public void process() {

            if(new Random().nextInt(4) == 0) {
                broken = true;
            }
            if(!broken) product();
        }
    }
}
