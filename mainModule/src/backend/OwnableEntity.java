package backend;

import backend.worldBuilding.Player;
import backend.worldBuilding.Location;
import jdk.nashorn.internal.objects.NativeUint16Array;

import java.io.Serializable;

/**
 * Represents an Entity owned by a Player.
 */
public abstract class OwnableEntity extends Entity implements Serializable {
    private Player owner=null;

    public OwnableEntity(Location inLocation, Player inOwner){
        super(inLocation);
        this.owner=inOwner;
    }

    /**
     * Sets the Player who owns the Entity.
     * @param owner owner of the Entity.
     */
    public void setOwner(Player owner){
        this.owner=owner;
    }

    /**
     * Returns the owner of the Entity-
     * @return Player who owns the Entity.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns true if the Entity has an owner.
     * @return True if it has an owner, false if not.
     */
    public boolean hasOwner(){
        return owner != null;
    }
}

