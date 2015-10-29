package frontend;

import backend.building.BuildingType;
import backend.building.Income;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;

@Deprecated
public class CellUIData {
    private Location location;


    private Terrain terrain;

    private Integer health, maxHealth;//health/
    private UnitType unitType;
    private BuildingType buildingType;
    private Integer ownerID;

    private boolean isSelected;

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

    public Integer getOwnerID() {
        return ownerID;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public CellUIData(Location location, Terrain terrain) {
        this.location = location;
        this.terrain = terrain;
    }
    public void addBuildingData(BuildingType buildingType, Integer ownerID){
        this.buildingType = buildingType;
        this.ownerID = ownerID;
    }

    public void addUnitData(UnitType unitType, Integer health, Integer maxHealth, Integer ownerID) {
        this.unitType = unitType;
        this.health = health;
        this.maxHealth = maxHealth;
        this.ownerID = ownerID;

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void selectCell() {
        isSelected = true;
    }
}
