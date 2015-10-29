package backend.building;

/**
 * This class represents the logic form of a Mine.
 */
public class Mine extends Building{

    /**
     * Creates a Mine object. Its type is "Mine".
     * @param gold The gold per turn that this Mine object generates.
     */
    public Mine(){
        super(BuildingType.MINE, null, new MineIncome());
    }
}
