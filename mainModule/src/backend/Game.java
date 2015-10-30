package backend;

import backend.building.Building;
import backend.building.Castle;
import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class Game implements Serializable {
    private World world;
    private Location selectedLocation;
    private Player player1, player2;
    private Player activePlayer;
    private Queue<String> logQueue;

    public Game(Integer worldWidth, Integer worldHeight, String player1, String player2) {
        loadPlayers(player1,player2);
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        startNewGame();
    }

    public Game(World world, String player1,String player2){
        loadPlayers(player1,player2);
        startNewGame();
    }

    public void loadPlayers(String player1, String player2){
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    public Integer getWorldHeight() {
        return world.getWorldHeight();
    }

    public Integer getWorldWidth() {
        return world.getWorldWidth();
    }

    public void startNewGame() {
        logQueue = new ArrayDeque<>();
        activePlayer = this.player1;
        selectPlayerCastle(activePlayer);
    }

    private boolean selectPlayerCastle(Player player) {
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        if (world.getPlayerCastle(player) == null) {
            addLog(player + "lost, he has no more buildings");
            return false;
        } else selectedLocation = world.getPlayerCastle(player).getLocation();
        return true;
    }

    public void actionAttempt(Location clickedLocation) {
        if (selectedLocation == null) {
            selectedLocation = clickedLocation;
            return;
        }

        if (world.isUnitOnLocation(selectedLocation)) {
            Unit selectedUnit = world.getUnitAt(selectedLocation);
            if (world.isUnitOnLocation(clickedLocation)) {
                if (world.getUnitAt(clickedLocation).getOwner().equals(activePlayer)) {
                    setSelectedLocation(clickedLocation);
                    System.out.println("location: " + selectedLocation);
                } else if (!selectedLocation.equals(clickedLocation)) {
                    attackAttempt(selectedUnit, world.getUnitAt(clickedLocation));
                }
            } else {
                //No unit and building means unit tries to capture
                //getOwnerID() can be null if its a neutral mine
                //TODO: add building.hasOwner()
                if ( world.isBuildingOnLocation(clickedLocation)){
                    Building currentBuilding = world.getBuildingAt(clickedLocation);
                    if ( currentBuilding.getOwner() == null || !(currentBuilding.getOwner().equals(activePlayer))){
                        captureAttempt(selectedUnit,clickedLocation);
                    }
                }else if (selectedUnit.getOwner().equals(activePlayer)) { //Fixes bug that can move opponents units
                    moveAttempt(selectedUnit, clickedLocation);
                }
                selectedLocation = clickedLocation;
            }
        } else {
            //building is selected
            selectedLocation = clickedLocation;
        }
    }

    public boolean attemptBuildArcher() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildArcher(world);
            addLog("Archer Built");
            return true;
        }
        return false;
    }

    public boolean attemptBuildRider() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildRider(world);
            addLog("Rider Built");
            return true;
        }
        return false;
    }

    public boolean attemptBuildLancer() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildLancer(world);
            addLog("Lancer Built");
            return true;
        }
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
    private boolean moveAttempt(Unit unit, Location clickedLocation) {
        //TODO ask how we can display a log
        boolean hasMoved = false;

        if (unit == null) {
            throw new NullArgumentException("null unit movement attempt");
        }
        if (clickedLocation == null) {
            throw new NullArgumentException("null to cell movement attempt");
        }

        return unit.move(clickedLocation);
    }

    private boolean captureAttempt(Unit unit, Location clickedLocation) {
        boolean hasCaptured = false;

        if (moveAttempt(unit, clickedLocation)) {
            world.getBuildingAt(clickedLocation).setOwner(unit.getOwner());
            addLog(unit + " captured " + world.getBuildingAt(clickedLocation));
            hasCaptured = true;
        }
        addLog("cant capture");
        return hasCaptured;
    }

    public boolean attackAttempt(Unit attacker, Unit defender) {
        boolean hasAttacked = false;

        if (attacker == null) {
            throw new NullArgumentException("null attacker");
        }
        if (defender == null) {
            throw new NullArgumentException("null defender");
        }

        if (attacker.getOwner().equals(defender.getOwner())) {
            throw new IllegalStateException("tries to attack own unit");
        }
        if (attacker.attack(defender)) {
            addLog(attacker + " attacked " + defender);
            hasAttacked = true;
        } else {
            addLog(attacker + " Cant Attack ");
        }
        return hasAttacked;
    }

    private void setSelectedLocation(Location location) {
        selectedLocation = location;
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public void printLog() {
        while (!logQueue.isEmpty()) {
            System.out.println(logQueue.poll());
        }
    }

    public Collection<Unit> getUnits() {
        return world.getUnits();
    }

    public Collection<Building> getBuildings() {
        return world.getBuildings();
    }

    public Collection<Cell> getCells() {
        return world.getCells();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    private void activateNextPlayer() {
        activePlayer = activePlayer.equals(player1) ? player2 : player1;
    }

    public void endTurn() {
        world.refillUnitsAP(getActivePlayer());

        getActivePlayer().addGold(world.getPlayerIncome(getActivePlayer()));

        Unit selectedUnit = world.getUnitAt(world.getPlayerCastle(activePlayer).getLocation());
        if ( selectedUnit.hasHolyGrail() ){

        }

        activateNextPlayer();
        if (!selectPlayerCastle(getActivePlayer())) {
            addLog("The game has ended, please do not move anything");
            activateNextPlayer();
        }
        addLog(getActivePlayer() + " has " + getActivePlayer().getGold() + " gold and received " +
                world.getPlayerIncome(getActivePlayer()));
    }

    /**
     * Attempts to make a Unit, if there is a unit there, pick an item in the current Cell.
     */
	public void pickItemAttempt() {

        if (selectedLocation == null) {
            return;
        }
        if ( world.isUnitOnLocation(selectedLocation) ){
            Unit currentUnit = world.getUnitAt(selectedLocation);
            if ( currentUnit.getOwner().equals(activePlayer) ){
                currentUnit.pickItem();
            }
        }
    }
}
