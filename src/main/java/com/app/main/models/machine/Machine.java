package com.app.main.models.machine;

import com.app.main.models.Inventory;
import com.app.main.models.ressources.RessourceEnum;

public abstract class Machine {
    
    protected long production_duration;
    private long production_start;
    
    protected RessourceEnum product;
    protected Inventory inventory = new Inventory();

    protected long capacity;

    protected final void product() {

        if(product != null && 
            inventory.countRessource(product) <= capacity && 
            production_start + production_duration <= System.currentTimeMillis()) {
            inventory.addRessource(product);
        }
    }

    public final void startProduct(RessourceEnum product) {
        this.product = product;
        production_start = System.currentTimeMillis();
    }

    public abstract void process();
}
