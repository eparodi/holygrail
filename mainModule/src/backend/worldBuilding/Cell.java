package backend.worldBuilding;


import backend.items.Item;
import backend.items.ItemFactory;
import backend.items.ItemType;
import backend.terrain.Terrain;
import backend.Entity;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Cell extends Entity {

    private Queue<Item> treasures;
    private final static int MAX_ITEMS = 5;
    private Terrain terrain;

    /**
     * Constructs a new Cell, on a Location, with certain Terrain and random Items.
     *
     * @param location Location of the Cell in the World.
     * @param terrain Terrain of the Cell.
     */
    public Cell(Location location, Terrain terrain) {
        super(location);
        this.terrain = terrain;
        this.treasures = new LinkedList<>();

        Random random = new Random();
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
       // return "Cell at " + ((location == null) ? "null location" : location.toString()) + "terrain type: " + terrain;
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
        treasures.add(addItem);
    }

    public boolean canRecieveItem(){
        if(treasures.size() >= MAX_ITEMS)
            return false;
        else
            return getTerrain().canRecieveItem();
    }
}
