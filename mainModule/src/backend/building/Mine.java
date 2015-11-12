package backend.building;

import backend.worldBuilding.Location;

import java.io.Serializable;

/**
 * This class represents the logic form of a Mine.
 */
public class Mine extends Building implements Serializable {
    /**
     * Constructs a Mine with its location in the map.
     *
     * @param location location of the mine.
     */
    public Mine(Location location) {
        super(null, new MineIncome(), location);
    }

    @Override
    public String toString() {
        return "Mine from " + getOwner();
    }
}
