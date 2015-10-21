package backend.units;

import backend.Attack;
import backend.Defense;
import backend.exceptions.NoSuchUnitTypeException;
import backend.exceptions.NullNameException;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

//TODO (ToAsk) hacer con String o calses speparadas por tipo de unidad

/**
 * This class is used to generate new Unit objects.
 */
public class UnitFactory {

    /**
     * Creates a new Unit.
     * @param unitType       the type of the Unit.
     * @param currentTerrain the current Terrain of the Unit.
     * @param location       the current position of the Unit.
     * @param player         the player who owns this Unit.
     * @return the Unit that was created.
     * @throws NullNameException if the UnitType is null.
     * @throws NoSuchUnitTypeException if the UnitType is not valid.
     */
    //Constructor: name, baseAttack, maxHealth, maxActionPoints, range, currentTerrain, preferredTerrain, location, owner
    public static Unit buildUnit(UnitType unitType, Terrain currentTerrain, Location location, Player player) {

        Unit unit = null;
        if (unitType == null) throw new NullNameException("Null unit name");

        switch (unitType){
            case ARCHER:
                unit = new Unit(UnitType.ARCHER , new Attack(1, 6, 1), new Defense(1,4,2), 10, 4, 2, Terrain.FOREST,
                        currentTerrain, location, player);
                break;

            case LANCER:
                unit = new Unit(UnitType.LANCER, new Attack(2, 2, 5), new Defense(3,2,3), 15, 5, 1, Terrain.GRASS,
                        currentTerrain, location, player);
                break;

            case RIDER:
                unit = new Unit(UnitType.RIDER, new Attack(3, 2, 4), new Defense(2,4,1), 15, 7, 1, Terrain.GRASS,
                        currentTerrain, location, player);
                break;
        }

        //TODO agregar otros

        if (unit == null){
            throw new NoSuchUnitTypeException("No unit type called '" + unitType + "'");
        }
        return unit;
    }
}
