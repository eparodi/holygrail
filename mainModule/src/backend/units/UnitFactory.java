package backend.units;

import backend.Attack;
import backend.Defense;
import backend.exceptions.NoSuchUnitTypeException;
import backend.exceptions.NullNameException;
import backend.terrain.TerrainType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

/**
 * This class is used to generate new Unit objects.
 */
@Deprecated
public class UnitFactory {
    public static Archer buildArcher(World world, Location location, Player player) {
        return new Archer(world, location, player);
    }

    public static Lancer buildLancer(World world, Location location, Player player) {
        return new Lancer(world, location, player);
    }

    public static Rider buildRider(World world, Location location, Player player) {
        return new Rider(world, location, player);
    }
}
