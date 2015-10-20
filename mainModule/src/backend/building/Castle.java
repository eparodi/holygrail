package backend.building;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

/**
 * This class represents the logic form of a Castle.
 */
public class Castle extends Building {

    /**
     * Constructs a Castle object. Its income per turn is 10 gold coins and its type is "Castle".
     * @param player The player who owns the castle.
     */
    public Castle(Player player) {
        owner = player;
        buildingType = "Castle";
        perTurnGoldIncome = 10;
    }

    /**
     * Creates a Unit object using the UnitFactory class.
     * @param  unitType The type of the unit.
     * @param  terrain The terrain where the unit currently is.
     * @param  location The position of the unit.
     * @param  player The player who owns the unit.
     * @return The unit that the Castle has created.
     * @see    UnitFactory
     * @see    Unit
     * @see    UnitType
     */
    public Unit buildUnit(UnitType unitType, Terrain terrain, Location location, Player player) {
        return UnitFactory.buildUnit(unitType, terrain, location, player);
    }

}
