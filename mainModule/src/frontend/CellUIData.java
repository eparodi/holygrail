package frontend;

import backend.units.Unit;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;


public class CellUIData {
    Location location;
    Terrain terrain;
    Integer health, maxHealth;
    String unitName;
    String buildingType;

    public String getUnitName() {
        return unitName;
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
    public void addUnitData(String unitName){
        this.unitName = unitName;
    }
}
