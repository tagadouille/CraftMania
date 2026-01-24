package com.app.main.models;

import java.util.HashMap;
import java.util.Stack;

import com.app.main.models.resources.Resource;
import com.app.main.models.resources.ResourceEnum;

/**
 * Class Inventory representing an inventory
 */
public final class Inventory {
    private HashMap<ResourceEnum, Stack<Resource>> inventory;

    /**
     * Constructor of Inventory
     */
    public Inventory(){
        inventory = new HashMap<>();
    }

    public HashMap<ResourceEnum, Stack<Resource>> getInventory() {
        return inventory;
    }

    /**
     * Method for add a resource in the inventory
     * @param resource
     */
    public void addResource(ResourceEnum resource){
        if(!this.inventory.containsKey(resource)){
            this.inventory.put(resource, new Stack<>());
        }
        this.inventory.get(resource).add(resource.getResource());
    }

    /**
     * Method for remove a resource of the inventory
     * @param resource
     * @return the removed resource
     */
    public Resource removeResource(ResourceEnum resource){
        if(this.inventory.containsKey(resource)){
            Stack<Resource> stack = this.inventory.get(resource);
            if(stack.size() != 0){
                return stack.pop();
            }
        }
        return null;
    }

    /**
     * Method to count the number of a specific resource in the inventory
     * @param resource the type of resource to count
     * @return the count of the resource
     */
    public int countResource(ResourceEnum resource) {
        return inventory.get(resource).size();
    }
}
