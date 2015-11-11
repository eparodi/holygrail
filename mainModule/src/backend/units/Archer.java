package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

/**
 * Represents an Archer Unit of the game.
 */
public class Archer extends Unit implements Serializable {

    /**
     * Creates an Archer in the World, on certain Location, with an owner.
     *
     * @param world    World where the Archer is.
     * @param location Location of the archer.
     * @param owner    Player who owns the archer.
     */
    public Archer(World world, Location location, Player owner) {
        super(new Attack(1, 6, 1), new Defense(1, 4, 2), 10, 4, 2, world, location, owner, 3, 5);
    }

    @Override
    public String toString() {
        return "Archer";
    }
}
