package backend.units;

import backend.Attack;
import backend.Defense;
import backend.terrain.TerrainType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

public class Rider extends Unit implements Serializable {

    public Rider(World world, Location location, Player owner){
        super(UnitType.RIDER, new Attack(3, 2, 4), new Defense(2,4,1), 15, 7, 1, world, location, owner,5,5);
    }
}
