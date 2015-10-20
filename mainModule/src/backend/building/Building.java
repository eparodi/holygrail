package backend.building;

import backend.worldBuilding.Player;

/**
 * This abstract class is the logic base to create all the constructions.
 * Each building has its type and a player who is the owner.
 */

public abstract class Building {
    String buildingType;
    Player owner = null;
    Integer perTurnGoldIncome;

    /**
     * This method is used to return the gold this building generates.
     * @return The amount of gold the player receive from this building per turn.
     */
    public Integer getPerTurnGoldIncome(){
        return perTurnGoldIncome;
    }

    /**
     * This method returns the owner of the building.
     * @return The player who owns the Building.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * This method sets the owner of the building.
     * @param owner This is the Player who will own the building.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * This method returns the type of building.
     * @return It represents the type of the building.
     */
    public String getBuildingType(){
        return buildingType;
    }

    /**
     * This method returns the string representation of the object.
     * @return It represents the object.
     */
    public String toString(){
        return buildingType + " from " + owner + "; ";
    }
}
