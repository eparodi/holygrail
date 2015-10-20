package backend.building;

import backend.worldBuilding.Player;

/**
 * This class is the logic base to create all the constructions. Each building has its type and a player who is
 * the owner.
 */

public abstract class Building {
    String buildingType;
    Player owner = null;
    Integer perTurnGoldIncome;

    /**
     * Returns the gold this building generates.
     * @return The amount of gold the player receive from this building per turn.
     */
    public Integer getPerTurnGoldIncome(){
        return perTurnGoldIncome;
    }

    /**
     * Returns the owner of the building.
     * @return The player who owns the Building.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the building.
     * @param owner The Player who will own the building.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Returns the type of building.
     * @return the type of the building.
     */
    public String getBuildingType(){
        return buildingType;
    }

    /**
     * Returns the String representation of the object.
     * @return the String representation of the object.
     */
    public String toString(){
        return buildingType + " from " + owner + "; ";
    }
}
