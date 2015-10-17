package Tests;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import org.junit.Test;


public class MoveTest {

    @Test
    public void test() {
        Player p1 = new Player("Pablo");
        Player p2 = new Player("Sergio");
        World world = new World(5, 5, p1, p2);

        Unit archer = UnitFactory.buildUnit("archer", Terrain.GRASS, new Location(3, 1), p1);

        world.addUnit(archer, archer.getLocation());

        world.moveUnit(archer.getLocation(), new Location(3, 3));
    }

}
