package Tests;

import backend.Attack;
import backend.building.Castle;
import backend.items.Item;
import backend.items.ItemFactory;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {
    Player p1;
    Player p2;
    World world;
    Unit archer;

    @Before
    public void initialisation() {
        p1 = new Player("Pablo");
        p2 = new Player("Sergio");
        world = new World(50, 50, p1, p2);

        archer = UnitFactory.buildUnit(UnitType.ARCHER, Terrain.GRASS, new Location(3, 0), p1);
    }

    @Test
    //Sees if a Unit can Move
    public void MoveTest() {
        world.addUnit(archer);
        world.moveUnit(archer.getLocation(), new Location(3, 3));

        assertTrue(archer.getLocation().equals(new Location(3, 3)));
        assertTrue(world.getCellAt(archer.getLocation()).getTerrain().equals(archer.getCurrentTerrain()));
    }

    @Test
    //Sees if a unit can capture a castle
    public void CaptureCastleTest() {
        Castle castle = new Castle(p2);

        world.addUnit(archer);
        world.addBuilding(castle, new Location(1, 1));

        world.captureBuilding(archer, new Location(1, 1));

        assertTrue(castle.getOwner().equals(p1));
    }

    @Test
    //Sees if two units attack each other
    public void BattleTest() {
        Unit lancer = UnitFactory.buildUnit(UnitType.WARRIOR, Terrain.GRASS, new Location(3, 1), p2);

        world.addUnit(archer);
        world.addUnit(lancer);

        Integer lancerHealthIni = lancer.getHealth();
        Integer archerHealthIni = archer.getHealth();

        world.skirmish(archer, lancer);

        assertTrue(!lancer.getHealth().equals(lancerHealthIni) && !archer.getHealth().equals(archerHealthIni));
    }

    @Test
    //Sees if a Unit can pickup an item
    public void PickupTest() {
        //Extra item:
        Unit archer1 = UnitFactory.buildUnit(UnitType.ARCHER, Terrain.GRASS, new Location(3, 1), p1);
        world.addUnit(archer1);

        Integer maxHealthIni = archer1.getMaxHealth();
        Integer healthIni = archer1.getHealth();

        Item potion = ItemFactory.buildExtra("potion");
        archer1.pickItem(potion);

        assertTrue(!maxHealthIni.equals(archer1.getMaxHealth()) && !healthIni.equals(archer1.getHealth()));

        world.removeUnit(archer1);

        //Rune item
        Unit archer2 = UnitFactory.buildUnit(UnitType.ARCHER, Terrain.GRASS, new Location(3, 1), p1);
        world.addUnit(archer2);

        Attack attackIni = archer2.getAttack();

        Item rune = ItemFactory.buildRune("fire rune");
        archer2.pickItem(rune);

        assertTrue(!attackIni.equals(archer2.getAttack()));

        world.removeUnit(archer2);

        //TODO ver si hacemos armor pickupeable
    }
}
