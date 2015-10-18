package Tests;

import backend.Game;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Pedro on 17/10/2015.
 */
public class GameTest {

    Game game = new Game();

    @Before
    public void initialisation() {
        game.startNewGame(20, 20, "Sergio", "Pablo");
    }

    @Test
    //Two units in range form same player
    public void test1() {
        game.attemptBuildUnit("lancer");
        game.actionAttempt(new Location(2, Math.round(20 / 2)));
        game.actionAttempt(new Location(1, Math.round(20 / 2)));//esta es la posicion del castillo
        game.attemptBuildUnit("archer");
        game.actionAttempt(new Location(2, Math.round(20 / 2)));

        assertTrue(game.getSelectedCell().getUnit().getName().equals("Lancer"));
    }

    @Test
    //Unit has been created
    public void test2() {
        game.attemptBuildUnit("lancer");

        assertTrue(game.getSelectedCell().hasUnit());
    }

    @Test
    //Unit moved 1 cell
    public void test3(){

        game.attemptBuildUnit("lancer");
        game.actionAttempt(new Location(2, 10));

        assertTrue(game.getSelectedCell().hasUnit());


    }
}
