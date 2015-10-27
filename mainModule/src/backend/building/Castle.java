package backend.building;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;

public class Castle extends Building {

    /**
     * Constructs a Castle Building, owned by certain Player.
     *
     * @param player Owner of the Castle.
     */
    public Castle(Player player) {
        owner = player;
        buildingType = "Castle";
        perTurnGoldIncome = 10;
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
    public Unit buildUnit(UnitType unitType, World world, Location location, Player player) {
        return UnitFactory.buildUnit(unitType, world, location, player);
    }

}
