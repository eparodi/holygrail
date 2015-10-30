package backend.building;

import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;

/**
 * This class is the logic base to create all the constructions. Each building has its type and a player who is
 * the owner.
 */

public abstract class Building implements Serializable{
    BuildingType buildingType;
    Player owner = null;
    Income income;
    Location location;

    public Location getLocation() {
        return location;
    }

    public Building(BuildingType buildingType, Player owner, Income income, Location location){
        this.buildingType = buildingType;
        this.owner = owner;
        this.income = income;
        this.location = location;
    }

    /**
     * Returns the gold this building generates.
     * @return The amount of gold the player receive from this building per turn.
     */
    public Integer getIncome(){
        return income.giveIncome();
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
    public BuildingType getBuildingType(){
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
