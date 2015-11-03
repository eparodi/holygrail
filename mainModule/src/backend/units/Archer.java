package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

public class Archer extends Unit implements Serializable {

    public Archer(World world, Location location, Player owner){
        super(new Attack(1, 6, 1), new Defense(1,4,2), 10, 4, 2, world, location, owner,3,5);
    }
}
