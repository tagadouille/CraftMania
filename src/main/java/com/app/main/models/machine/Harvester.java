package com.app.main.models.machine;

import java.util.Random;

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
     * @param capacity the capacity of the harvester
     * @param production_duration the duration of production cycle
     */
    private Harvester(int price, long capacity, long production_duration) {
        super(price, capacity, production_duration);
        this.typeName = "harvester";
    }

    /**
     * Constructor for Harvester class.
     * @param price the price of the harvester
     */
    private Harvester(int price) {
        this(price, 100, 5000);
    }

    /**
     * Constructor for Harvester class.
     * @param price the price of the harvester
     * @param capacity the capacity of the harvester
     */
    private Harvester(int price, long capacity){
        this(price, capacity, 5000);
    }

    @Override
    public void process() {
        product();
    }
    
    /**
     * Factory method to create a SimpleHarvester.
     * @return a new SimpleHarvester instance
     */
    public static SimpleHarvester createSimpleHarvester() {
        return new SimpleHarvester();
    }

    /**
     * Factory method to create a FastHarvester.
     * @return a new FastHarvester instance
     */
    public static FastHarvester createFastHarvester() {
        return new FastHarvester();
    }

    /**
     * Factory method to create a XLHarvester.
     * @return a new XLHarvester instance
     */
    public static XLHarvester createXLHarvester() {
        return new XLHarvester();
    }

    /**
     * Factory method to create a WeakHarvester.
     * @return a new WeakHarvester instance
     */
    public static WeakHarvester createWeakHarvester() {
        return new WeakHarvester();
    }

    /**
     * Factory method to create a PolyHarvester.
     * @return a new PolyHarvester instance
     */
    public static PolyHarvester createPolyHarvester() {
        return new PolyHarvester();
    }

    /**
     * SimpleHarvester class representing a basic harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class SimpleHarvester extends Harvester {

        private SimpleHarvester() {
            super(100);
        }
    }

    /**
     * SimpleHarvester class representing a fast harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class FastHarvester extends Harvester {
        
        private FastHarvester() {
            super(250, 150, 2500);
        }
    }

    /**
     * SimpleHarvester class representing a large capacity harvester.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class XLHarvester extends Harvester {
        
        private XLHarvester() {
            super(175, 350);
        }
    }

    /**
     * SimpleHarvester class representing a polyvalent harvester that can be configured multiple times.
     * Extends the Harvester class.
     * @see Harvester
     * @author Dai Elias
     */
    public final static class PolyHarvester extends Harvester {

        private PolyHarvester() {
            super(150);
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
        
        private WeakHarvester() {
            super(50);
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

        /**
         * Repair the harvester to make it operational again.
         */
        public void repair() {
            broken = false;
        }
    }
}
