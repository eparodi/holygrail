package backend;

import backend.building.Castle;
import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.units.UnitType;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;
import frontend.CellUIData;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class Game {
    public static final Integer ATTACK_AP_COST = 2;
    private World world;
    private Cell selectedCell;
    private Player player1, player2;
    private Player activePlayer;
    private Queue<String> logQueue;

    public Game(Integer worldWidth, Integer worldHeight, String player1, String player2) {
        startNewGame(worldWidth,worldHeight,player1,player2);
    }

    public Integer getWorldHeight(){
        return world.getWorldHeight();
    }
    public Integer getWorldWidth(){
        return world.getWorldWidth();
    }

    public void startNewGame(Integer worldWidth, Integer worldHeight, String player1, String player2) {
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        logQueue = new ArrayDeque<String>();
        activePlayer = this.player1;
        selectPlayerCastle(activePlayer);
    }

    private boolean selectPlayerCastle(Player player) {
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        if(world.getPlayerCastle(player) == null){
            addLog(player + "lost, he has no more buildings");
            return false;
        }
        else selectedCell = world.getCellAt(world.getBuildingLocation(world.getPlayerCastle(player)));
        return true;
    }

    public void actionAttempt(Location location) {
        actionAttempt(world.getCellAt(location));
    }

    public void actionAttempt(Cell clickedCell) {
        if (selectedCell == null) {
            selectedCell = clickedCell;
            return;
        }
        if (selectedCell.hasUnit()) {
            if (clickedCell.hasUnit()) {
                if (clickedCell.getUnit().getOwner().equals(activePlayer)) {
                    setSelectedCell(clickedCell);
                } else if(selectedCell.getUnit().getOwner().equals(activePlayer)){
                    attackAttempt(selectedCell.getUnit(), clickedCell.getUnit());
                }else{
                    setSelectedCell(clickedCell);
                }
            } else if(getSelectedCell().getUnit().getOwner().equals(getActivePlayer())){
                //No unit and building means unit tries to capture
                if (clickedCell.hasBuilding() && !clickedCell.getBuilding().getOwner().equals(activePlayer)) {
                    captureAttempt(selectedCell.getUnit(), clickedCell);
                    //TODO: CODIGO REPETIDO
                    selectedCell = clickedCell;
                } else {
                    moveAttempt(selectedCell.getUnit(), clickedCell);
                    selectedCell = clickedCell;
                }
            }
            else {setSelectedCell(clickedCell);}
        } else {
            //building is selected
            selectedCell = clickedCell;
        }
        addLog(getSelectedCell().toString());
        printLog();
    }

    public boolean attemptBuildUnit(UnitType unitType) {
        if (unitType == null){
            throw new NullArgumentException("Unit name is null");
        }
        Castle castle = world.getPlayerCastle(activePlayer);
        Cell castleCell = world.getCellAt(world.getBuildingLocation(world.getPlayerCastle(activePlayer)));

        //TODO precio en castillo?
        if (!castleCell.hasUnit()){
            if(activePlayer.canPay(10)) {
                Unit unitCreated = castle.buildUnit(unitType, castleCell.getTerrain(), castleCell.getLocation(), activePlayer);
                world.addUnit(unitCreated);
                //TODO precio en castillo?
                activePlayer.pay(10);
                return true;
            }addLog(getActivePlayer() + " has less than 10 gold" + getActivePlayer().getGold());
        }addLog("Building at " + castleCell.getLocation() + " is occupied");
        addLog("Successfully build a " + unitType + " in " + castleCell.getLocation());

        return false;
    }

    //currentSelected to null
    public void deselect() {
        //TODO implement
    }

    private void addLog(String msg) {
        logQueue.add(msg);
    }

    //Returns if it moved
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

        if (attacker == null) throw new NullArgumentException("null attacker");
        if (defender == null) throw new NullArgumentException("null defender");

        if (attacker.getOwner().equals(defender.getOwner()))
            throw new IllegalStateException("tries to attack own unit");
        if(attacker.equals(defender))
            throw new IllegalStateException("tries to attack itself");

        if (world.isInRange(attacker, defender)) {
            if (attacker.getActionPoints() >= ATTACK_AP_COST) {
                attacker.spendAP(ATTACK_AP_COST);
                world.skirmish(attacker, defender);
                addLog(attacker + " attacked " + defender);
                hasAttacked = true;
            } else addLog(attacker + " has not enough energy ");
        } else addLog(attacker + " is not in range ");
        return hasAttacked;
    }

    private void setSelectedCell(Cell cell) {
        selectedCell = cell;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void printLog(){
        while(!logQueue.isEmpty()){
            System.out.println(logQueue.poll());
        }
    }

    public Collection<CellUIData> getCellUIData(){
        return world.generateCellUIData();
    }

    public Player getActivePlayer(){
        return activePlayer;
    }

    private void activateNextPlayer(){
        activePlayer = activePlayer.equals(player1)? player2:player1;
    }
    public void endTurn(){
        world.refillUnitsAP(getActivePlayer());

        getActivePlayer().addGold(world.getPlayerIncome(getActivePlayer()));
        activateNextPlayer();
        if(! selectPlayerCastle(getActivePlayer())) {
            addLog("The game has ended, please do not move anything");
            activateNextPlayer();
        }
        addLog(getActivePlayer() + " has " + getActivePlayer().getGold() + " gold and recieved " +
                world.getPlayerIncome(getActivePlayer()));
    }
}
