package backend.building;

import backend.worldBuilding.Location;

/**
 * This class represents the logic form of a Mine.
 */
public class Mine extends Building{


    public Mine(Location location){
        super(BuildingType.MINE, null, new MineIncome(),location);
    }
}
