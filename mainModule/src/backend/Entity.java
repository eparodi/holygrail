package backend;

import backend.worldBuilding.Location;

import java.io.Serializable;

/**
 * Represents an Entity in certain Location of the World.
 */
public abstract class Entity implements Serializable{
    protected Location location;

    //TODO: Clase abstracta con constructor?
    public Entity(Location newLoc){
        this.location=newLoc;
    }

    /**
     * Returns the Location of the entity in the world.
     * @return entity location.
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public String toString(){
        return location.toString();
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}

