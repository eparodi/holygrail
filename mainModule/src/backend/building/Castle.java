package backend.building;

import backend.worldBuilding.Location;
import backend.worldBuilding.Player;

import java.io.Serializable;

/**
 * Represents a Castle building. It's a production building which in case of being captured by the enemy, makes
 * the player who owned it to lose the game.
 */
public class Castle extends ProductionBuilding implements Serializable {

    /**
     * Constructs a Castle Building, owned by certain Player.
     *
     * @param owner Owner of the Castle.
     */
    public Castle(Player owner, Location location) {
        super(owner, new CastleIncome(), location);
    }

    @Override
    public String toString() {
        return "Castle from " + getOwner();
    }
}
