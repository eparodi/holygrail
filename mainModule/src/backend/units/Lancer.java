package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

public class Lancer extends Unit implements Serializable {

    public Lancer(World world, Location location, Player owner){
        super(new Attack(5, 2, 2), new Defense(3,2,3), 15, 5, 1, world,location, owner,5,3);
    }
}
