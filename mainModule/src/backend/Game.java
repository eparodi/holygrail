package backend;

import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;

import java.util.ArrayDeque;
import java.util.Queue;

public class Game {
    public static final Integer ATTACK_AP_COST=2;
    World world;
    Cell selectedCell;
    Player player1, player2;
    Player currentPlayer;
    Queue<String> logQueue;

    public Game() {

    }

    public void startNewGame(Integer worldWidth, Integer worldHeight, String player1, String player2) {
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        logQueue = new ArrayDeque<String>();
        selectPlayerCastle(currentPlayer);
    }

    public void selectPlayerCastle(Player player) {
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        selectedCell = world.getCellAt(world.getBuildingLocation(world.getPlayerCastle(currentPlayer)));
    }

    public void actionAttempt(Cell clickedCell) {
        if (selectedCell == null) {
            selectedCell = clickedCell;
            return;
        }
        if (selectedCell.isUnitOnCell()) {
            if (clickedCell.isUnitOnCell()) {
                if (clickedCell.getUnit().getOwner().equals(currentPlayer)) setSelectedCell(clickedCell);
                else attackAttempt(selectedCell.getUnit(), clickedCell.getUnit());
            } else {
                //No unit and building means unit tries to capture
                if (clickedCell.isBuildingOnCell()) captureAttempt(selectedCell.getUnit(), clickedCell);
                else moveAttempt(selectedCell.getUnit(), clickedCell);
            }
        } else {
            selectedCell = clickedCell;
        }
    }

    private void addLog(String msg) {
        logQueue.add(msg);
    }

    private boolean moveAttempt(Unit unit, Cell clickedCell) {
        //TODO ask how we can display a log
        boolean hasMoved = false;

        if (unit == null) throw new NullArgumentException("null unit movement attempt");
        if (clickedCell == null) throw new NullArgumentException("null to cell movement attempt");

        Integer distance = World.distance(unit.getLocation(), clickedCell.getLocation());
        if (distance == 1) {
            Integer terrainAPCost = world.getTerrainAPCost(clickedCell.getTerrain());

            if (unit.getActionPoints() >= terrainAPCost) {
                world.moveUnit(unit.getLocation(), clickedCell.getLocation());
                unit.spendAP(terrainAPCost);
                addLog(unit + " has " + unit.getActionPoints() + " AP left");
                hasMoved = true;
            } else addLog(unit + " has not enough energy");
        } else addLog("You can only move 1 hex at a time");

        return hasMoved;
    }


    private boolean captureAttempt(Unit unit, Cell clickedCell) {
        boolean hasCaptured = false;

        if (moveAttempt(unit, clickedCell)) {
            clickedCell.getBuilding().setOwner(unit.getOwner());
            addLog(unit + " captured " + clickedCell.getBuilding());
            hasCaptured = true;
        }
        addLog("cant capture");
        return hasCaptured;
    }

    public boolean attackAttempt(Unit attacker, Unit defender) {
        boolean hasAttacked = false;

        if(attacker == null) throw new NullArgumentException("null attacker");
        if(defender == null) throw new NullArgumentException("null defender");

        if(attacker.getOwner().equals(defender.getOwner()))throw new IllegalStateException("tries to attack own unit");

        if( world.isInRange(attacker, defender)) {
            if( attacker.getActionPoints() >= ATTACK_AP_COST ) {
                attacker.spendAP(ATTACK_AP_COST);
                world.skirmish(attacker, defender);
                addLog(attacker + " attacked " + defender);
                hasAttacked = true;
            }else addLog(attacker + " has not enough energy ");
        } else addLog(attacker + " is not in range ");
        return hasAttacked;
    }

    public void setSelectedCell(Cell cell) {
        selectedCell = cell;
    }
}
