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
public class Rider extends Unit{

    public Rider(World world, Location location, Player owner){
        super(UnitType.RIDER, new Attack(3, 2, 4), new Defense(2,4,1), 15, 7, 1, world,  Terrain.GRASS,
                location, owner);
    }
}
