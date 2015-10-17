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
    World world;
    Cell selectedCell;
    Player player1, player2;
    Player currentPlayer;
    Queue<String> logQueue;

    public Game(){

    }

    public void startNewGame(Integer worldWidth, Integer worldHeight, String player1, String player2){
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        logQueue = new ArrayDeque<String>();
        selectPlayerCastle(currentPlayer);
    }

    public void selectPlayerCastle(Player player){
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        selectedCell = world.getCellAt( world.getBuildingLocation( world.getPlayerCastle(currentPlayer)));
    }

    public void actionAttempt(Cell clickedCell){
        if(selectedCell == null) {
            selectedCell = clickedCell;
            return;
        }
        if(selectedCell.isUnitOnCell()){
            if(clickedCell.isUnitOnCell()){
                if(clickedCell.getUnit().getOwner().equals(currentPlayer)) setSelectedCell(clickedCell);
                else attackAttempt(selectedCell.getUnit(),clickedCell.getUnit());
            }else {
                //No unit and building means unit tries to capture
                if(clickedCell.isBuildingOnCell()) captureAttempt(selectedCell.getUnit(),clickedCell);
                else moveAttempt(selectedCell.getUnit(), clickedCell);
            }
        }
        else {
            selectedCell =clickedCell;
        }
    }

    private void addLog(String msg){
        logQueue.add(msg);
    }

    private boolean moveAttempt(Unit unit, Cell clickedCell){
        //TODO ask how we can display a log
        boolean hasMoved = false;

        if(unit == null) throw new NullArgumentException("null unit movement attempt");
        if(clickedCell == null) throw new NullArgumentException("null to cell movement attempt");

        Integer distance = World.distance(unit.getLocation(), clickedCell.getLocation());
        if(distance == 1) {
            Integer terrainAPCost = world.getTerrainAPCost(clickedCell.getTerrain());

            if(unit.getActionPoints() >= terrainAPCost){
                world.moveUnit(unit.getLocation(),clickedCell.getLocation());
                unit.spendAP(terrainAPCost);
                addLog(unit + " has " + unit.getActionPoints() + " AP left");
                hasMoved = true;
            }else addLog(unit + " has not enough energy");
        }
        else addLog("You can only move 1 hex at a time");

        return hasMoved;
    }


    private void captureAttempt(Unit unit, Cell selectedCell) {

    }

    public void attackAttempt(Unit attacker, Unit defender){

    }

    public void setSelectedCell(Cell cell){
        selectedCell = cell;
    }
}
