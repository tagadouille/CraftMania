package com.app.main.models.machine;

import java.util.Random;

import com.app.main.models.ressources.RessourceEnum;

public sealed abstract class Harvester extends Machine permits com.app.main.models.machine.Harvester.SimpleHarvester, com.app.main.models.machine.Harvester.FastHarvester, com.app.main.models.machine.Harvester.WeakHarvester, com.app.main.models.machine.Harvester.PolyHarvester, com.app.main.models.machine.Harvester.XLHarvester{

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
            this.production_duration = 5000;
            this.capacity = 100;
            this.product = product;
            startProduct(product);
        }
    }

    public final static class FastHarvester extends Harvester {
        
        public FastHarvester(RessourceEnum product) {
            this.production_duration = 2500;
            this.capacity = 100;
            this.product = product;
            startProduct(product);
        }
    }

    public final static class XLHarvester extends Harvester {
        
        public XLHarvester(RessourceEnum product) {
            this.production_duration = 5000;
            this.capacity = 200;
            this.product = product;
            startProduct(product);
        }
    }

    public final static class PolyHarvester extends Harvester {

        public PolyHarvester(RessourceEnum product) {
            this.production_duration = 5000;
            this.capacity = 100;
            this.product = product;
            startProduct(product);
        }
    }

    public final static class WeakHarvester extends Harvester{

        private boolean broken = false;
        
        public WeakHarvester(RessourceEnum product) {
            this.production_duration = 5000;
            this.capacity = 100;
            this.product = product;
            startProduct(product);
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
