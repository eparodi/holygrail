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
public class Archer extends Unit{

    public Archer(World world, Location location, Player owner){
        super(UnitType.ARCHER, new Attack(1, 6, 1), new Defense(1,4,2), 10, 4, 2, world,  Terrain.FOREST,
                location, owner);
    }
}
