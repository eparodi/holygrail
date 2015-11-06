package backend.building;

import backend.worldBuilding.Location;

import java.io.Serializable;

/**
 * This class represents the logic form of a Mine.
 */
public class Mine extends Building implements Serializable {
    public Mine(Location location){
        super(null, new MineIncome(),location);
    }

    @Override
    public String toString(){
        return "Castle from " + getOwner();
    }
}
