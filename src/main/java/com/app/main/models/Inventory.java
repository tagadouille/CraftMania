package com.app.main.models;

import java.util.HashMap;
import java.util.Stack;

import com.app.main.models.ressources.Ressource;
import com.app.main.models.ressources.RessourceEnum;

public class Inventory {
    private HashMap<RessourceEnum, Stack<Ressource>> inventory;

    public Inventory(){
        inventory = new HashMap<>();
    }
    public HashMap<RessourceEnum, Stack<Ressource>> getInventory() {
        return inventory;
    }
    /**
     * Method for add a ressource in the inventory
     * @param ressource
     */
    public void addRessource(RessourceEnum ressource){
        if(!this.inventory.containsKey(ressource)){
            this.inventory.put(ressource, new Stack<>());
        }
        this.inventory.get(ressource).add(ressource.getRessource());
    }
    /**
     * Method for remove a ressource of the inventory
     * @param ressource
     * @return
     */
    public Ressource removeRessource(RessourceEnum ressource){
        if(this.inventory.containsKey(ressource)){
            Stack<Ressource> stack = this.inventory.get(ressource);
            if(stack.size() != 0){
                return stack.pop();
            }
        }
        return null;
    }
}
