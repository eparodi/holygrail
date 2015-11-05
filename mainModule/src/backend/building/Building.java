package backend.building;

import backend.OwneableEntitty;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;

import java.io.Serializable;

/**
 * This class is the logic base to create all the constructions. Each building has its type and a player who is
 * the owner.
 */

public abstract class Building extends OwneableEntitty implements Serializable{
    private BuildingType buildingType;
    private Income income;

    public Building(BuildingType buildingType, Player owner, Income income, Location location){
        super(location,owner);
        this.buildingType = buildingType;
        this.income = income;
    }

    /**
     * Returns the gold this building generates.
     * @return The amount of gold the player receive from this building per turn.
     */
    public Integer getIncome(){
        return income.giveIncome();
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
        return buildingType + " from " + getOwner() + "; ";
    }
}
