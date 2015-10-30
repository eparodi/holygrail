package backend.units;

import backend.Attack;
import backend.Defense;
import backend.terrain.TerrainType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;
import backend.worldBuilding.World;

public class Archer extends Unit{

    public Archer(World world, Location location, Player owner){
        super(UnitType.ARCHER, new Attack(1, 6, 1), new Defense(1,4,2), 10, 4, 2, world, location, owner,3,5);
    }
}
