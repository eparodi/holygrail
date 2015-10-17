package backend.worldBuilding;


import backend.building.Building;
import backend.exceptions.CellIsEmpty;
import backend.exceptions.CellIsOccupiedException;
import backend.units.Unit;

public class Cell {
    Location location;
    Unit localUnit;
    Building building;
    Terrain terrain;

    public Cell(Location location, Terrain terrain)
    {
        this.terrain = terrain;
        this.location = location;
    }

    public Unit getUnit(){
        return localUnit;
    }

    public Location getLocation(){
        return location;
    }

    public boolean isUnitOnCell(){
        return !(localUnit == null);
    }

    public void addUnit(Unit unit){
        if(isUnitOnCell()) throw new CellIsOccupiedException("Cell at " + location.toString() + " is occupied");
        localUnit = unit;
    }

    public void removeUnit(){
        if (isUnitOnCell() == false){
            throw new CellIsEmpty("Cell at " + location.toString() + " is empty");
        }
        localUnit = null;
    }

    public String toString(){
        return "Cell at " + location.toString() + "terrain type: " +terrain+ " unit: " + localUnit.toString();
    }
}
