package Tests;

import backend.Game;
import backend.worldBuilding.Location;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest {

    Game game;

    @Before
    public void initialisation() {
        game = new Game(20,20,"Sergio","Pablo");
    }

//    @Test
    //Two units in range form same player
//    public void test1() {
//        game.attemptBuildUnit(UnitType.LANCER);
//        game.actionAttempt(new Location(2, Math.round(20 / 2)));
//        game.actionAttempt(new Location(1, Math.round(20 / 2)));//esta es la posicion del castillo
//        game.attemptBuildUnit(UnitType.ARCHER);
//        game.actionAttempt(new Location(2, Math.round(20 / 2)));
//
//        assertTrue(game.getSelectedLocation().getUnit().getUnitType().equals(UnitType.LANCER));
//    }
//
//    @Test
//    //Unit has been created
//    public void test2() {
//        game.attemptBuildUnit(UnitType.LANCER);
//
//        assertTrue(game.getSelectedLocation().hasUnit());
//    }
//
//    @Test
//    //Unit moved 1 cell
//    public void test3(){
//
//        game.attemptBuildUnit(UnitType.LANCER);
//        game.actionAttempt(new Location(2, 10));
//
//        assertTrue(game.getSelectedLocation().hasUnit());
//
//
//    }
//
}
