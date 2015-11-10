package backend.worldBuilding;


import backend.exceptions.NullArgumentException;
import backend.items.Item;
import backend.items.ItemFactory;
import backend.terrain.Terrain;
import backend.Entity;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Represents a Cell in the World. It has a specific Terrain and Items you can get by digging.
 */
public class Cell extends Entity {

    private Queue<Item> treasures;
    private final static int MAX_ITEMS = 5;
    private Terrain terrain;
    private static Random random;

    /**
     * Constructs a new Cell, on a Location, with certain Terrain and random Items.
     *
     * @param location Location of the Cell in the World.
     * @param terrain Terrain of the Cell.
     */
    public Cell(Location location, Terrain terrain) {
        super(location);
        this.terrain = terrain;
        this.treasures = new LinkedList<Item>();

        random = new Random();
        int numberOfItems = random.nextInt(MAX_ITEMS);

        for ( int i = 0 ; i <= numberOfItems ; i++ ){
            treasures.add(ItemFactory.buildRandomItem());
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return ((Cell) o).getLocation().equals(this.getLocation());
    }

    @Override
    public int hashCode() {
        return super.getLocation().hashCode();
    }

    /**
     * Returns the Terrain of the Cell.
     *
     * @return Terrain of the Cell.
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Returns the Location of the Cell.
     *
     * @return location of the cell.
     */
    public Location getLocation() {
        return super.getLocation();
    }

    public String toString() {
        return  super.getLocation().toString() + "terrain type: " + terrain;
    }

    /**
     * Adds the Holy Grail item to the current Cell.
     */
    public void addHolyGrail(){
        treasures.add(ItemFactory.buildItem("Holy Grail", -2, 3, 0, 0, 0));
    }

    /**
     * Get the first item in the Treasures Queue.
     * @return First item in the Treasures Queue.
     */
    public Item getItem(){
        if ( treasures.isEmpty() ){
            return null;
        }else{
            return treasures.remove();
        }
    }

    /**
     * Adds an item to the Treasures Queue.
     * @param addItem Item added to the Treasures Queue
     */
    public void addItem( Item addItem ){
        if(addItem == null) throw new NullArgumentException("Item cant be null");
            treasures.add(addItem);
    }

    /**
     * Returns true if the Cell can hold one more item, and false if not.
     * @return True if the Cell can hold another item.
     */
    public boolean canReceiveItem(){
        if(treasures.size() >= MAX_ITEMS)
            return false;
        else
            return getTerrain().canReceiveItem();
    }
}
