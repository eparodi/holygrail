package backend;

import backend.exceptions.NullLocationException;
import backend.worldBuilding.Location;

import java.io.Serializable;

public abstract class Entity implements Serializable{
    protected Location location;

    public Entity(Location newLoc){
        this.location=newLoc;
    }

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

