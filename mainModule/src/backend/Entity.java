package backend;

import backend.exceptions.NullLocationException;
import backend.worldBuilding.Location;

public abstract class Entity {
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




    public String toString(){
        return location.toString();
    }

    public int hashCode() {
        return location.hashCode();
    }
}

