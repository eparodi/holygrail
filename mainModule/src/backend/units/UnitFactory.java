package backend.units;

import backend.Attack;
import backend.exceptions.NoSuchUnitType;
import backend.exceptions.NullNameException;
import backend.items.ItemFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;


public class UnitFactory {
    public UnitFactory() {

    }

    //Constructor: name, baseAttack, maxHealth, maxActionPoints, range, currentTerrain, preferredTerrain, location, owner
    public static Unit buildUnit(String unitName, Terrain currentTerrain, Location location, Player player) {

        Unit unit = null;
        if (unitName == null) throw new NullNameException("Null unit name");

        if (unitName.equalsIgnoreCase("Archer")) {
            unit = new Unit("Archer", new Attack(1, 4, 1), 10, 7, 2, Terrain.FOREST, currentTerrain, location, player);

            ItemFactory itemFactory = new ItemFactory();
            unit.pickItem(itemFactory.buildArmor("shining armor"));
        }

        if (unitName.equalsIgnoreCase("Lancer")) {
            unit = new Unit("Lancer", new Attack(2, 2, 7), 20, 10, 1, Terrain.GRASS, currentTerrain, location, player);

            ItemFactory itemFactory = new ItemFactory();
            unit.pickItem(itemFactory.buildArmor("shining armor"));
        }

        //TODO agregar otros

        if (unit == null) throw new NoSuchUnitType("No unit type called '" + unitName + "'");
        return unit;
    }
}
