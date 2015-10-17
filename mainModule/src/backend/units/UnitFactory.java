package backend.units;

import backend.Attack;
import backend.Defense;
import backend.exceptions.NoSuchUnitType;
import backend.exceptions.NullNameException;
import backend.items.ItemFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

//TODO (ToAsk) hacer con String o calses speparadas por tipo de unidad
public class UnitFactory {

    //Constructor: name, baseAttack, maxHealth, maxActionPoints, range, currentTerrain, preferredTerrain, location, owner
    public static Unit buildUnit(String unitName, Terrain currentTerrain, Location location, Player player) {

        Unit unit = null;
        if (unitName == null) throw new NullNameException("Null unit name");

        if (unitName.equalsIgnoreCase("Archer")) {
            unit = new Unit("Archer", new Attack(1, 4, 1), new Defense(1,4,2), 10, 7, 2, Terrain.FOREST, currentTerrain,
                    location, player);

            ItemFactory itemFactory = new ItemFactory();
        }

        if (unitName.equalsIgnoreCase("Lancer")) {
            unit = new Unit("Lancer", new Attack(2, 2, 7), new Defense(3,2,3), 20, 10, 1, Terrain.GRASS, currentTerrain,
                    location, player);

            ItemFactory itemFactory = new ItemFactory();
        }

        //TODO agregar otros

        if (unit == null) throw new NoSuchUnitType("No unit type called '" + unitName + "'");
        return unit;
    }
}
