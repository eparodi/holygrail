package backend;

import backend.exceptions.NullLocationException;
import backend.worldBuilding.Location;

import java.io.Serializable;

public abstract class Entity implements Serializable{
    private Location location;

    public Entity(Location newLoc){
        this.location=newLoc;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) throw new NullLocationException(this.toString() + " received a null location");
        this.location = location;
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

