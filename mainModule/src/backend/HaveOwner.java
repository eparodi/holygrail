package backend;

import backend.worldBuilding.Player;
import backend.worldBuilding.Location;


public class HaveOwner extends Entity {
    private Player Owner;

    public HaveOwner(Player inOwner,Location inLocation){
        super(inLocation);
        this.Owner=inOwner;
    }
}
