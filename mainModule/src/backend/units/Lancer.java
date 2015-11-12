package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

/**
 * Represents a Lancer Unit of the game.
 */
public class Lancer extends Unit implements Serializable {

    /**
     * Creates a Lancer in the World, on certain Location, with an owner.
     *
     * @param world    World where the Lancer is.
     * @param location Location of the Lancer.
     * @param owner    Player who owns the Lancer.
     */
    public Lancer(World world, Location location, Player owner) {
        super(new Attack(5, 2, 2), new Defense(3, 2, 3), 15, 5, 1, world, location, owner, 5, 3);
    }

    @Override
    public String toString() {
        return "Lancer from "+ getOwner();
    }
}
