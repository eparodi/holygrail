package backend.building;

import backend.terrain.TerrainFactory;
import backend.terrain.TerrainType;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Player;

import java.util.ArrayList;

/**
 * Created by Julian Benitez on 10/29/2015.
 */
public class ProductionBuilding extends Building {
    public ProductionBuilding(BuildingType buildingType, Player owner, Income income) {
        super(buildingType, owner, income);
    }

    /**
     * Creates a Unit.
     *
     * @param unitType Type of the Unit.
     * @param terrain Current terrain of the Castle Cell.
     * @param location Location of the Unit.
     * @param player Owner of the Unit.
     * @return A new Unit.
     */
    //TODO Fix this when merging with unit
    public Unit buildArcher(){
        return UnitFactory.buildUnit(UnitType.ARCHER, TerrainFactory.buildForestTerrain(),location,owner);
    }
}
