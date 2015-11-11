package backend.building;

import backend.OwnableEntity;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;

import java.io.Serializable;

/**
 * This class is the logic base to create all the constructions. Each building player who is
 * the owner.
 */

public abstract class Building extends OwnableEntity implements Serializable{
    private Income income;

    public Building(Player owner, Income income, Location location){
        super(location,owner);
        if(location==null) throw new NullArgumentException("Building has to have a location");
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
     * Returns the String representation of the object.
     * @return the String representation of the object.
     */
    public String toString(){
        return "Building from " + getOwner() + "; ";
    }
}
