package backend.worldBuilding;

import backend.exceptions.CellOutOfWorldException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class World {
    Collection<Cell> cells;
    Integer worldWidth, worldHeight;

    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2){
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection(worldWidth,worldHeight);

       // Location player1Castle = new Location()

    }
    public  Cell getCellAt(Location location){
        for (Cell cell: cells){
            if (cell.getLocation().equals(location)) return cell;
        }
        throw new CellOutOfWorldException("No cell exists at " + location.toString());
    }

    public  Collection<Unit> getUnits(){
        Collection<Unit> units = new ArrayList<Unit>();
        Unit unit;

        for(Cell cell:cells){
            unit = cell.getUnit();
            if(!(unit == null)) units.add(unit);
        }
        return units;
    }

    public Collection<Unit> getUnits(Player player){
        Collection<Unit> units = new ArrayList<Unit>();
        Unit unit;

        for(Cell cell:cells){
            unit = cell.getUnit();
            if((!(unit == null)) && unit.getOwner().equals(player)) units.add(unit);
        }

        return units;
    }

    public Terrain loadTerrain(Location location){
        return Terrain.GRASS;
    }

    private Collection<Cell> generateCellCollection(Integer worldWidth, Integer worldHeight){

        Collection<Cell> cellCollection = new ArrayList<Cell>();
        Cell cell;
        Location cellLocation;

        for (int i=0 ; i < worldWidth ; i++){
            for (int j=0 ; j < worldHeight ; j++) {
                
                cellLocation = new Location(i,j);
                cell = new Cell(cellLocation, loadTerrain(cellLocation));

                cellCollection.add(cell);
            }
        }
        return cellCollection;
    }
}
