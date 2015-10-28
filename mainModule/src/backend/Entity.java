package backend;

import backend.worldBuilding.Location;

public class Entity {
    Location location;

    public Entity(Location newLoc){
        this.location=newLoc;
    }

    public Location getLocation() {
        return location;
    }
    public String toString(){
        return location.toString();
    }

    public int hashCode() {
        return location.hashCode();
    }
}

