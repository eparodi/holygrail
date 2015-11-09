package Tests;

import backend.building.Castle;
import backend.building.CastleIncome;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.NullArgumentException;
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

import static org.junit.Assert.assertFalse;
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


    @Test(expected = CellOutOfWorldException.class)
    public void incorrectCastleLocationTest(){
        Castle castle2  = new Castle(p1, new Location(60,60));
        world.addBuilding(castle2);
        assertTrue(world.isBuildingOnLocation(castle2.getLocation()));
    }

    @Test(expected = NullArgumentException.class)
    public void nullCastle(){
        Castle castle2  = new Castle(p1, null);
        world.addBuilding(castle2);
        assertTrue(world.isBuildingOnLocation(null));
    }

    @Test
    public void buildTest(){
        castle.buildArcher(world);
        assertTrue(world.isUnitOnLocation(castle.getLocation()));
    }

    @Test
    public void cellOcupiedUnitBuildingTest(){
        castle.buildArcher(world);

        assertFalse(castle.canBuild(world));
    }
    @Test
    public void LowGoldUnitBuildingTest(){
        castle.getOwner().pay(castle.getOwner().getGold());
        assertFalse(castle.canBuild(world));
    }

    @Test
    public void CastleIncomeTest(){
        Integer castleIncome = new CastleIncome().giveIncome();
        assertTrue(castle.getIncome() == castleIncome);
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
