package Tests;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import org.junit.Before;

public class WorldTest {
    Player p1;
    Player p2;
    World world;
    Unit archer;


    @Before
    public void initialisation(){
        p1 = new Player("Pablo");
        p2 = new Player("Sergio");
        world = new World(50, 50, p1, p2);
    }
}
