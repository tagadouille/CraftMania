package com.app.main.models.map;

import java.util.Optional;

import com.app.main.models.Item;
import com.app.main.models.machine.Machine;
import com.app.main.models.resources.Resource;

/**
 * Represents a tile on the map with a specific type and an optional item.
 * Each tile can be of various types such as EMPTY, RESOURCE, START, etc.
 * It can also hold an item if applicable.
 * @see TileType
 * @see Item
 * @author Dai Elias
 */
public class Tile {

    private TileType type;
    private Item item;

    /**
     * Types of tiles available in the map.
     */
    public enum TileType {
        EMPTY, RESOURCE, START, MARKET, FACTORY, HARVESTER, RESOURCETMP;
    }

    private Tile(TileType type){
        this.type = type;
    }

    private Tile(TileType type, Item item){
        this(type);
        this.item = item;
    }

    /**
     * Factory method to create a Tile instance.
     * @param type the type of the tile
     * @param item the item on the tile, can be null if there is no item
     * @return a new Tile instance
     */
    public static Tile createTile(TileType type, Item item){

        if(item != null){
            return new Tile(type, item);
        }
        return new Tile(type);
    }

    /* Getters and setters : */
    public TileType getType() {
        return type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Returns an Optional containing the Machine on the tile if it exists 
     * and is of the correct type, otherwise returns an empty Optional.
     */
    public Optional<Machine> getMachine(){

        if(this.item == null){
            return Optional.empty();
        }

        if(this.type == TileType.FACTORY || this.type == TileType.HARVESTER){
            return Optional.of((Machine) this.item);
        }
        return null;
    }

   /**
    * Returns an Optional containing the Resource on the tile if it exists
    * and is of the correct type, otherwise returns an empty Optional.
    */
    public Optional<Resource> getResource(){

        if(this.item == null){
            return Optional.empty();
        }

        if(this.type == TileType.RESOURCE || this.type == TileType.RESOURCETMP){
            return Optional.of((Resource) this.item);
        }
        return null;
    }

    /**
     * Checks if the tile is accessible based on its type.
     * @return true if the tile is of type START or EMPTY, false otherwise.
     */
    public boolean isAccessible(){
        return this.type == TileType.START || this.type == TileType.EMPTY;
    }
}
