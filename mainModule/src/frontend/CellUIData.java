package frontend;

import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;


public class CellUIData {
    Location location;
    Terrain terrain;
    Integer health, maxHealth;
    UnitType unitType;
    String buildingType;

    public UnitType getUnitType() {
        return unitType;
    }

    public Location getLocation() {
        return location;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Integer getHealth() {
        return health;
    }

    public Integer getMaxHealth() {
        return maxHealth;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public CellUIData(Location location, Terrain terrain) {
        this.location = location;
        this.terrain = terrain;
    }
    public void addBuildingData(String buildingType){
        this.buildingType = buildingType;
    }
    public void addUnitData(UnitType unitType){
        this.unitType = unitType;
    }
}
