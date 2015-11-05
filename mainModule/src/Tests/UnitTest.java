package Tests;
import backend.building.Castle;
import backend.building.Mine;
import backend.terrain.Terrain;
import backend.units.Archer;
import backend.units.Lancer;
import backend.units.Unit;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;
import org.junit.*;

import static org.junit.Assert.assertTrue;

public class UnitTest {
    Player p1 = new Player("Pablo");
    Player p2 = new Player("Sergio");
    World world;
    Unit archer;
    Unit lancer;


    @Before
    public void initialisation() {
        world = new World(50, 50, p1, p2);
        archer = new Archer(world,new Location(1,3),p1);
        lancer = new Lancer(world,new Location(2,3),p2);
        //world.addUnit(archer);
        //world.addUnit(lancer);
    }

    @Test
    //Sees if a Unit can Move
    public void MoveTest() {
        archer.move(new Location(2, 2));
        System.out.println("playerID: " + p1.getId());

        assertTrue(archer.getLocation().equals(new Location(2, 2)));
        assertTrue(world.getCellAt(archer.getLocation()).getTerrain().equals(archer.getCurrentTerrain()));
    }

    @Test
    //Sees if a Unit cant move
    public void CantMoveTest() {
        world.addUnit(archer);
        archer.move(new Location(40, 40));
        System.out.println("playerID: " + p1.getId());

        assertTrue(!archer.getLocation().equals(new Location(40, 40)));
        assertTrue(world.getCellAt(archer.getLocation()).getTerrain().equals(archer.getCurrentTerrain()));
    }



    @Test
    //Sees if a unit can capture a castle
    public void CaptureCastleTest() {
        Castle castle = new Castle(p2, new Location(1,4));

        world.addUnit(archer);
        world.addBuilding(castle);

        archer.move(new Location(1, 4));
        assertTrue(castle.getOwner().equals(p1));
    }

    @Test
    //Sees if a unit can capture a castle
    public void CaptureMineTest() {
        Mine mine = new Mine(new Location(1,4));

        world.addUnit(archer);
        world.addBuilding(mine);
        archer.move(new Location(1, 4));
        assertTrue(mine.getOwner().equals(p1));
    }

    @Test
    //Sees if two units attack each other
    public void BattleTest() {

        Integer lancerHealthIni = lancer.getHealth();
        Integer archerHealthIni = archer.getHealth();

        archer.attack(lancer);

        //world.skirmish(archer, lancer, null, null);

        assertTrue(!lancer.getHealth().equals(lancerHealthIni) && !archer.getHealth().equals(archerHealthIni));
    }

    @Test
    //Sees if two units attack each other
    public void DeathTest() {
        lancer.move(new Location(3, 3));

        archer.attack(lancer);
        archer.refillAP();
        archer.attack(lancer);
        archer.refillAP();
        archer.attack(lancer);
        archer.refillAP();
        archer.attack(lancer);
        archer.refillAP();
        archer.attack(lancer);
        archer.refillAP();
        archer.attack(lancer);

        //world.skirmish(archer, lancer, null, null);

        assertTrue(!world.isUnitOnLocation(new Location(3,3)));
    }

    @Test
    public void PickupTest() {

        world.getCellAt(new Location(1,3)).addHolyGrail();
        archer.pickItem();
        assertTrue(archer.hasItem());
    }


}
