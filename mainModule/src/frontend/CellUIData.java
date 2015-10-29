package frontend;

import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;


public class CellUIData {
    private Location location;
    private Terrain terrain;
    private Integer health, maxHealth;
    private UnitType unitType;
    private String buildingType;
    private Player owner;
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

    public Player getOwner() {
        return owner;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public CellUIData(Location location, Terrain terrain) {
        this.location = location;
        this.terrain = terrain;
    }
    public void addBuildingData(String buildingType, Player owner){
        this.buildingType = buildingType;
        this.owner = owner;
    }

    public void addUnitData(UnitType unitType, Integer health, Integer maxHealth, Player owner) {
        this.unitType = unitType;
        this.health = health;
        this.maxHealth = maxHealth;
        this.owner = owner;

    }

    public boolean isSelected() {
        return isSelected;
    }

    public void selectCell() {
        isSelected = true;
    }
}
