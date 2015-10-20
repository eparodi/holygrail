package backend.building;

import backend.worldBuilding.Player;

/**
 * This class represents the logic form of a Mine.
 */
public class Mine extends Building{

    /**
     * Creates a Mine object. Its type is "Mine".
     * @param gold The gold per turn that this Mine object generates.
     */
    public Mine(Integer gold){
        buildingType = "Mine";
        perTurnGoldIncome = gold;
    }

}
