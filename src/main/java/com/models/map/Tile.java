package main.java.com.models.map;

public class Tile {

    private TileType type;
    private Item item;

    public Tile(TileType type){
        this.type = type;
    }
    public Tile(TileType type, Item item){
        this(type);
        this.item = item;
    }
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
    public boolean isAccessible(){
        return this.type == TileType.START || this.type == TileType.EMPTY;
    }
}
