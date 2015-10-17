package Tests;

import backend.building.Castle;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildingTest {
    Player p1;
    Player p2;
    World world;

    @Before
    public void initialisation() {
        p1 = new Player("Pablo");
        p1 = new Player("Sergio");
        world = new World(50, 50, p1, p2);
    }

    @Test
    public void buildUnitTest(){
        Castle castle = new Castle(p1);
        world.addBuilding(castle, new Location(0,0));

        Unit unit = castle.buildUnit("lancer", Terrain.FOREST, new Location(1,1), p1);

        assertTrue(unit.getName().equals("Lancer"));
        assertTrue(unit.getLocation().equals(new Location(1,1)));
        assertTrue(unit.getOwner().equals(p1));
    }
}
