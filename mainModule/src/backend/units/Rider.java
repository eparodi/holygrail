package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

public class Rider extends Unit implements Serializable {

    public Rider(World world, Location location, Player owner){
        super(new Attack(2, 2, 4), new Defense(2,3,1), 15, 7, 1, world, location, owner,5,5);
    }
}
