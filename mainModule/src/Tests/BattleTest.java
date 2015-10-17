package Tests;

import backend.Attack;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import org.junit.Test;


public class BattleTest {

    @Test
    public void test() {
        Player p1 = new Player("Pablo");
        Player p2 = new Player("Sergio");
        World world = new World(5, 5, p1, p2);

        Unit archer = UnitFactory.buildUnit("archer", Terrain.GRASS, new Location(3, 1), p1);
        Unit lancer = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 2), p1);

        world.addUnit(archer, archer.getLocation());
        world.addUnit(lancer, lancer.getLocation());

        System.out.println("Vida inicial lancer: " + lancer.getHealth());
        System.out.println("Vida inicial archer: " + archer.getHealth());

        world.skirmish(archer, lancer);

        System.out.println("Vida final lancer: " + lancer.getHealth());
        System.out.println("Vida final archer: " + archer.getHealth());
    }
}
