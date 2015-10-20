package backend.building;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

public class Castle extends Building {

    public Castle(Player player) {
        owner = player;
        buildingType = "Castle";
        perTurnGoldIncome = 10;
    }

    public Unit buildUnit(UnitType unitType, Terrain terrain, Location location, Player player) {
        return UnitFactory.buildUnit(unitType, terrain, location, player);
    }

}
