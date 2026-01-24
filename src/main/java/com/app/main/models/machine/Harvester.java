package com.app.main.models.machine;

import java.util.Random;

import com.app.main.models.ressources.RessourceEnum;

public sealed abstract class Harvester extends Machine permits com.app.main.models.machine.Harvester.SimpleHarvester, com.app.main.models.machine.Harvester.FastHarvester, com.app.main.models.machine.Harvester.WeakHarvester, com.app.main.models.machine.Harvester.PolyHarvester, com.app.main.models.machine.Harvester.XLHarvester{

    public Harvester(int price, RessourceEnum product, long capacity, long production_duration) {
        super(price, product, capacity, production_duration);
        this.typeName = "harvester";
    }

    public Harvester(int price, RessourceEnum product) {
        this(price, product, 100,5000);
    }

    public Harvester(int price, RessourceEnum product, long capacity){
        this(price, product, capacity, 5000);
    }

    @Override
    public void process() {
        product();
    }
     
    public static SimpleHarvester createSimpleHarvester(RessourceEnum product) {
        return new SimpleHarvester(product);
    }

    public static FastHarvester createFastHarvester(RessourceEnum product) {
        return new FastHarvester(product);
    }

    public static XLHarvester createXLHarvester(RessourceEnum product) {
        return new XLHarvester(product);
    }

    public static WeakHarvester createWeakHarvester(RessourceEnum product) {
        return new WeakHarvester(product);
    }

    public static PolyHarvester createPolyHarvester(RessourceEnum product) {
        return new PolyHarvester(product);
    }

    public final static class SimpleHarvester extends Harvester {

        public SimpleHarvester(RessourceEnum product) {

            super(100, product);
        }
    }

    public final static class FastHarvester extends Harvester {
        
        public FastHarvester(RessourceEnum product) {
            super(250, product, 150, 2500);
        }
    }

    public final static class XLHarvester extends Harvester {
        
        public XLHarvester(RessourceEnum product) {
            super(175, product, 350);
        }
    }

    public final static class PolyHarvester extends Harvester {

        public PolyHarvester(RessourceEnum product) {
            super(150, product);
        }
    }

    public final static class WeakHarvester extends Harvester{

        private boolean broken = false;
        
        public WeakHarvester(RessourceEnum product) {
            super(50, product);
        }

        @Override
        public void process() {

            if(new Random().nextInt(4) == 0) {
                broken = true;
            }
            if(!broken) product();
        }
    }
}
