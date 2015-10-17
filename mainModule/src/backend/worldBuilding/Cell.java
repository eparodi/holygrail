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

    public Cell(Location location, Terrain terrain) {
        this.terrain = terrain;
        this.location = location;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Unit getUnit() {
        return localUnit;
    }

    public Location getLocation() {
        return location;
    }

    public Building getBuilding() {
        return building;
    }

    public boolean isUnitOnCell() {
        return !(localUnit == null);
    }

    public boolean isBuildingOnCell(){
        return !(building == null);
    }

    public void addUnit(Unit unit) {
        if (isUnitOnCell()) throw new CellIsOccupiedException("Cell at " + location.toString() + " has a unit already");
        localUnit = unit;
    }

    public void addBuilding(Building building) {
        if (!(this.building == null))
            throw new CellIsOccupiedException("Cell at " + location.toString() + " has a building already");
        this.building = building;
    }

    public void removeUnit() {
        if (!isUnitOnCell()) {
            throw new CellIsEmpty("Cell at " + location.toString() + " is empty");
        }
        localUnit = null;
    }

    public String toString() {
        return "Cell at " + ((location == null) ? "null location" : location.toString()) +
                "terrain type: " + terrain + " unit: " + ((localUnit == null) ? "no unit" : localUnit.toString()) +
                "building: " + ((building == null) ? "no building" : building.toString());
    }
}
