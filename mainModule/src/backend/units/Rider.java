package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

/**
 * Represents a Rider Unit of the game.
 */
public class Rider extends Unit implements Serializable {

    /**
     * Creates a Rider in the World, on certain Location, with an owner.
     * @param world World where the Rider is.
     * @param location Location of the Rider.
     * @param owner Player who owns the Rider.
     */
    public Rider(World world, Location location, Player owner){
        super(new Attack(2, 2, 4), new Defense(2,3,1), 15, 7, 1, world, location, owner,5,5);
    }

    @Override
    public String toString() {
        return "Rider";
    }
}
