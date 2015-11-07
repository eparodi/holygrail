package Tests;

import backend.building.Castle;
import backend.terrain.Terrain;
import backend.units.Archer;
import backend.units.Lancer;
import backend.units.Rider;
import backend.units.Unit;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BuildingTest {
    Player p1;
    Player p2;
    World world;
    Castle castle;

    @Before
    public void initialisation() {

        p1 = new Player("Pablo");
        p2 = new Player("Sergio");
        world = new World(50, 50, p1, p2);
        castle = new Castle(p1, new Location(20,20));
        world.addBuilding(castle);
    }
    //TODO: Acepta castillos en cualquier lado che
    @Test
    public void incorrectCastle(){
        Castle castle2  = new Castle(p1, new Location(60,60));
        assertTrue(world.isBuildingOnLocation(castle2.getLocation()));
    }


    @Test
    public void buildArcherTest(){

        castle.buildArcher(world);

        assertTrue(world.isUnitOnLocation(castle.getLocation()));
    }

    @Test
    public void buildLancerTest(){

        castle.buildLancer(world);

        assertTrue(world.isUnitOnLocation(castle.getLocation()));
    }

    @Test
     public void buildRiderTest(){

        castle.buildRider(world);

        assertTrue(world.isUnitOnLocation(castle.getLocation()));
    }
}
