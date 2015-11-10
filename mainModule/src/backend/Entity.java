package backend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;

import java.io.Serializable;

/**
 * Represents an Entity in certain Location of the World.
 */
public abstract class Entity implements Serializable{
    protected Location location;

    public Entity(Location newLoc){
        if(newLoc == null) throw new NullArgumentException("Entity has to have a location");
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

