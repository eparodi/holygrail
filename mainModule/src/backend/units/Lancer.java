package backend.units;

import backend.Attack;
import backend.Defense;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;

/**
 * Created by Pedro on 28/10/2015.
 */
public class Lancer extends Unit{

    public Lancer(World world, Location location, Player owner){
        super(UnitType.LANCER, new Attack(2, 2, 5), new Defense(3,2,3), 15, 5, 1, world, Terrain.GRASS,
                location, owner);
    }
}
