package backend;

import backend.building.Building;
import backend.building.Castle;
import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;
import javafx.scene.control.Alert;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

/**
 * Represents a Game session, it has a World and Players.
 */
public class Game implements Serializable {
    private World world;
    private Location selectedLocation;
    private Player player1, player2;
    private Player activePlayer;
    private Queue<String> logQueue;

    /**
     * Constructs a Game with a World of a specific Width and Height, and 2 players.
     * @param worldWidth Width of the World.
     * @param worldHeight Height of the World.
     * @param player1 Player 1.
     * @param player2 Player 2.
     */
    public Game(Integer worldWidth, Integer worldHeight, String player1, String player2) {
        loadPlayers(player1, player2);
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        startNewGame();
    }

    /**
     * Creates and loads the two players.
     * @param player1 Player 1.
     * @param player2 Player 2.
     */
    public void loadPlayers(String player1, String player2){
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    /**
     * Returns the World Height.
     * @return Integer value of world Height.
     */
    public Integer getWorldHeight() {
        return world.getWorldHeight();
    }

    /**
     * Return the World Width.
     * @return Integer value of the
     */
    public Integer getWorldWidth() {
        return world.getWorldWidth();
    }

    /**
     * Starts a New Game, setting the active player to Player 1, and selecting his Castle.
     */
    public void startNewGame() {
        logQueue = new ArrayDeque<>();
        activePlayer = this.player1;
        selectPlayerCastle(activePlayer);
    }

    //TODO: Hacer un metodo para revisar si el player sigue teniendo Castle, para separarlo de este.
    /**
     * Selects the specified Player's castle.
     * @param player owner of the Castle.
     * @return
     */
    private boolean selectPlayerCastle(Player player) {
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        if (world.getPlayerCastle(player) == null) {
            addLog(player + "lost, he has no more buildings");
            return false;
        } else selectedLocation = world.getPlayerCastle(player).getLocation();
        return true;
    }

    /**
     * //TODO: Esto debería estar en el controller.
     * @param clickedLocation
     */
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
                if (world.isBuildingOnLocation(clickedLocation)) {
                    Building currentBuilding = world.getBuildingAt(clickedLocation);
                    if (currentBuilding.getOwner() == null || !(currentBuilding.getOwner().equals(activePlayer))) {
                        moveAttempt(selectedUnit, clickedLocation);
                    }
                } else if (selectedUnit.getOwner().equals(activePlayer)) { //Fixes bug that can move opponents units
                    moveAttempt(selectedUnit, clickedLocation);
                }
                selectedLocation = clickedLocation;
            }
        } else {
            //building is selected
            selectedLocation = clickedLocation;
        }
    }

    /**
     * Attempts to build an Archer. If achieved, returns true.
     * @return True if the Archer is created, false if not.
     */
    public boolean attemptBuildArcher() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildArcher(world);
            addLog("Archer Built");
            return true;
        }
        return false;
    }

    /**
     * Attempts to build a Rider. If achieved, returns true.
     * @return True if the Rider is created, false if not.
     */
    public boolean attemptBuildRider() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildRider(world);
            addLog("Rider Built");
            return true;
        }
        return false;
    }

    /**
     * Attempts to build a Lancer. If achieved, returns true.
     * @return True if the Lancer is created, false if not.
     */
    public boolean attemptBuildLancer() {
        Castle castle = world.getPlayerCastle(activePlayer);
        if (castle.canBuild(world)) {
            castle.buildLancer(world);
            addLog("Lancer Built");
            return true;
        }
        return false;
    }

    /**
     * Adds a message to the log.
     * @param msg message to add.
     */
    private void addLog(String msg) {
        logQueue.add(msg);
    }

    /**
     * Attempts to move a unit to a Location.
     * @param unit unit to move.
     * @param clickedLocation destination Location.
     * @return True if the unit has moved, false if not.
     */
    private boolean moveAttempt(Unit unit, Location clickedLocation) {
        //TODO ask how we can display a log

        if (unit == null) {
            throw new NullArgumentException("null unit movement attempt");
        }
        if (clickedLocation == null) {
            throw new NullArgumentException("null to cell movement attempt");
        }

        return unit.move(clickedLocation);
    }

    /**
     * Attempts to perform an attack from an attacker unit to a defender.
     * @param attacker attacking unit.
     * @param defender defending unit.
     * @return True if the unit has attacked.
     */
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

    /**
     * //TODO: CONTROLLER
     * @param location
     */
    private void setSelectedLocation(Location location) {
        selectedLocation = location;
    }

    /**
     * //TODO : CONTROLLER
     * @return
     */
    public Location getSelectedLocation() {
        return selectedLocation;
    }

    /**
     * Prints the queued Log.
     */
    public void printLog() {
        while (!logQueue.isEmpty()) {
            System.out.println(logQueue.poll());
        }
    }

    /**
     * Returns all the Units in the World.
     * @return Collection of all the Units.
     */
    public Collection<Unit> getUnits() {
        return world.getUnits();
    }

    /**
     * Returns all the Units in the World.
     * @return Collection of all the Buildings.
     */
    public Collection<Building> getBuildings() {
        return world.getBuildings();
    }

    /**
     * Returns all the Cells in the World.
     * @return Collection of all the Cells.
     */
    public Collection<Cell> getCells() {
        return world.getCells();
    }

    /**
     * Returns the active Player.
     * @return current active Player.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Sets the next player to be the active player.
     */
    private void activateNextPlayer() {
        activePlayer = activePlayer.equals(player1) ? player2 : player1;
    }

    /** //TODO: El alert debería estar en el FrontEnd, el controller debería fijarse si algun jugador ganó y emitir el msg.
     * Ends the current Player turn.
     * Refills the player units action points, adds corresponding the gold per turn to the player.
     * If a unit is standing at his owner Castle with the Holy Grail, the player wins the game.
     * If the player does not own his Castle anymore, he loses the game.
     */
    public void endTurn() {
        world.refillUnitsAP(getActivePlayer());

        getActivePlayer().addGold(world.getPlayerIncome(getActivePlayer()));

        if (world.isUnitOnLocation(world.getPlayerCastle(activePlayer).getLocation())) {
            Unit selectedUnit = world.getUnitAt(world.getPlayerCastle(activePlayer).getLocation());
            if (selectedUnit.hasHolyGrail()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Message");
                alert.setHeaderText("Congratulations");
                alert.setContentText("You have won!, do not try to move any more units please, start new game");

                alert.showAndWait();
            }
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
     * If there is a unit there, pick an item in the current Cell.
     */
    public void pickItemAttempt() {

        if (selectedLocation == null) {
            return;
        }
        if (world.isUnitOnLocation(selectedLocation)) {
            Unit currentUnit = world.getUnitAt(selectedLocation);
            if (currentUnit.getOwner().equals(activePlayer)) {
                currentUnit.pickItem();
            }
        }
    }
}
