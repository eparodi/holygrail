package backend;

import backend.worldBuilding.Player;
import backend.worldBuilding.Location;


public abstract class OwneableEntitty extends Entity {
    private Player owner=null;

    public OwneableEntitty(Location inLocation,Player inOwner){
        super(inLocation);
        this.owner=inOwner;
    }

    public void setOwner(Player owner){
        this.owner=owner;
    }

    public Player getOwner() {
        return owner;
    }
}

