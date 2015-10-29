package backend.terrain;

import backend.exceptions.NullArgumentException;

public abstract class Terrain {
    private static final Integer baseCost=10;
    private TerrainType terrainType;

    private Double slashBonus;
    private Double piercingBonus;
    private Double bluntBonus;

    private Integer maxSpeed;
    private Integer enduranceCost;

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public Double getSlashBonus() {
        return slashBonus;
    }

    public Double getPiercingBonus() {
        return piercingBonus;
    }

    public Double getBluntBonus() {
        return bluntBonus;
    }

    public Terrain(TerrainType terrainType, Double slashBonus, Double piercingBonus, Double bluntBonus,
                   Integer maxSpeed, Integer enduranceCost) {
        this.terrainType = terrainType;
        this.slashBonus = slashBonus;
        this.piercingBonus = piercingBonus;
        this.bluntBonus = bluntBonus;
        this.maxSpeed = maxSpeed;
        this.enduranceCost = enduranceCost;
    }

    public Integer getApCost(Integer unitSpeed, Integer unitEndurance){
        if(unitSpeed == null)throw new NullArgumentException("null unit speed");
        if(unitEndurance == null)throw new NullArgumentException("null unit endurance");

        Integer totalApCost=0;
        Integer baseApCost=baseCost;
        while (baseApCost >0){
            totalApCost++;
            baseApCost -= Math.min(unitSpeed, maxSpeed)*Math.max(1, (unitEndurance - enduranceCost));
        }

        return totalApCost;
    }

    public boolean canRecieveItem() {
        return true;
    }
}

