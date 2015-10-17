package backend.worldBuilding;

import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class World {
    Collection<Cell> cells;
    Integer worldWidth, worldHeight;

    public World(Integer worldWidth, Integer worldHeight){
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection(worldWidth,worldHeight);

    }

    public Collection<Unit> getUnits(Player player){
        Collection<Unit> units = null;
        return units;
    }

    public Terrain getTerrainAt(Location location){
        return Terrain.GRASS;
    }

    private Collection<Cell> generateCellCollection(Integer worldWidth, Integer worldHeight){

        Collection<Cell> cellCollection = new ArrayList<Cell>();
        Cell cell;
        Location cellLocation;

        for (int i=0 ; i < worldWidth ; i++){
            for (int j=0 ; j < worldHeight ; j++) {
                
                cellLocation = new Location(i,j);
                cell = new Cell(cellLocation, getTerrainAt(cellLocation));

                cellCollection.add(cell);
            }
        }
        return cellCollection;
    }
}
