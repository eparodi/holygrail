package backend;

import backend.building.Building;
import backend.building.ProductionBuilding;
import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a Game session, it has a World and Players.
 */
public class Game implements Serializable {
    private World world;
    private Location selectedLocation;
    private Player player1, player2;
    private Player activePlayer;
    private Queue<String> logQueue;
    private Random random;
    private boolean hasGameEnded = false;

    /**
     * Constructs a Game with a World of a specific Width and Height, and 2 players.
     *
     * @param worldWidth  Width of the World.
     * @param worldHeight Height of the World.
     */
    public Game(Integer worldWidth, Integer worldHeight) {
        random = new Random();
        loadPlayers(generateRandomName(), generateRandomName());
        world = new World(worldWidth, worldHeight, this.player1, this.player2);
        startNewGame();
    }

    private String generateRandomName() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("Sergio");
        names.add("Jorge");
        names.add("Epord");
        names.add("Xyausporeidium");
        names.add("NepNep");
        names.add("074");
        names.add("Garrigo");
        names.add("Santiago");
        names.add("Macky");
        names.add("Pol");
        return names.get(random.nextInt(names.size()));
    }

    /**
     * Creates and loads the two players.
     *
     * @param player1 Player 1.
     * @param player2 Player 2.
     */
    private void loadPlayers(String player1, String player2) {
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
    }

    /**
     * Returns the World Height.
     *
     * @return Integer value of world Height.
     */
    public Integer getWorldHeight() {
        return world.getWorldHeight();
    }

    /**
     * Return the World Width.
     *
     * @return Integer value of the
     */
    public Integer getWorldWidth() {
        return world.getWorldWidth();
    }

    /**
     * Starts a New Game, setting the active player to Player 1, and selecting his Castle.
     */
    public void startNewGame() {
        logQueue = new ArrayDeque<String>();
        logQueue.add("Blue player: " + player1 + "                  Red player: " + player2);
        activePlayer = this.player1;
        selectPlayerCastle(activePlayer);
    }


    /**
     * Selects the specified Player's castle.
     *
     * @param player owner of the Castle.
     * @return
     */
    private boolean selectPlayerCastle(Player player) {
        //Searches the castle from the first player and selects the cell where it is located
        //#Building needs location
        if (!hasCastle(player)) {
            addLog(player + "lost, he has no more buildings");
            return false;
        } else selectedLocation = player.getProductionBuilding().getLocation();
        return true;
    }

    public boolean hasCastle(Player player) {
        return player.getProductionBuilding().getOwner() == player;
    }

    /**
     * @param clickedLocation
     */
    public void actionAttempt(Location clickedLocation) {
        if (hasGameEnded) return;
        if (selectedLocation == null) {
            selectedLocation = clickedLocation;
            return;
        }

        if (world.isUnitOnLocation(selectedLocation)) {
            Unit selectedUnit = world.getUnitAt(selectedLocation);
            if (world.isUnitOnLocation(clickedLocation)) {
                if (world.getUnitAt(clickedLocation).getOwner().equals(activePlayer)) {
                    setSelectedLocation(clickedLocation);
                } else if (!selectedLocation.equals(clickedLocation)) {
                    attackAttempt(selectedUnit, world.getUnitAt(clickedLocation));
                }
            } else {
                if (selectedUnit.getOwner().equals(activePlayer)) { //Fixes bug that can move opponents units
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
     *
     * @return True if the Archer is created, false if not.
     */
    public void attemptBuildArcher() {
        ProductionBuilding productionBuilding = activePlayer.getProductionBuilding();
        addLog(productionBuilding.buildArcher(world));
    }

    /**
     * Attempts to build a Rider. If achieved, returns true.
     *
     * @return True if the Rider is created, false if not.
     */
    public void attemptBuildRider() {
        ProductionBuilding productionBuilding = activePlayer.getProductionBuilding();
        addLog(productionBuilding.buildRider(world));
    }

    /**
     * Attempts to build a Lancer. If achieved, returns true.
     *
     * @return True if the Lancer is created, false if not.
     */
    public void attemptBuildLancer() {
        ProductionBuilding productionBuilding = activePlayer.getProductionBuilding();
        addLog(productionBuilding.buildLancer(world));
    }

    /**
     * Adds a message to the log.
     *
     * @param msg message to add.
     */
    private void addLog(String msg) {
        logQueue.add(msg);
    }

    /**
     * Attempts to move a unit to a Location.
     *
     * @param unit            unit to move.
     * @param clickedLocation destination Location.
     * @return True if the unit has moved, false if not.
     */
    private void moveAttempt(Unit unit, Location clickedLocation) {
        if (unit == null) {
            throw new NullArgumentException("null unit movement attempt");
        }
        if (clickedLocation == null) {
            throw new NullArgumentException("null to cell movement attempt");
        }

        addLog(unit.move(clickedLocation));

    }

    /**
     * Attempts to perform an attack from an attacker unit to a defender.
     *
     * @param attacker attacking unit.
     * @param defender defending unit.
     * @return True if the unit has attacked.
     */
    private boolean attackAttempt(Unit attacker, Unit defender) {
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
     * @param location
     */
    private void setSelectedLocation(Location location) {
        selectedLocation = location;
    }

    /**
     * @return
     */
    public Location getSelectedLocation() {
        return selectedLocation;
    }

    /**
     * @return next string in log (returns null if empty.
     */

    public String getNextLog() {
        return logQueue.poll();
    }

    public boolean logHasNext() {
        return !logQueue.isEmpty();
    }

    /**
     * Returns all the Units in the World.
     *
     * @return Collection of all the Units.
     */
    public Collection<Unit> getUnits() {
        return world.getUnits();
    }

    /**
     * Returns all the Units in the World.
     *
     * @return Collection of all the Buildings.
     */
    public Collection<Building> getBuildings() {
        return world.getBuildings();
    }

    /**
     * Returns all the Cells in the World.
     *
     * @return Collection of all the Cells.
     */
    public Collection<Cell> getCells() {
        return world.getCells();
    }

    /**
     * Returns the active Player.
     *
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

    /**
     * Ends the current Player turn.
     * Refills the player units action points, adds corresponding the gold per turn to the player.
     * If a unit is standing at his owner Castle with the Holy Grail, the player wins the game.
     * If the player does not own his Castle anymore, he loses the game.
     */
    public boolean endTurn() {
        world.refillUnitsAP(getActivePlayer());

        getActivePlayer().addGold(world.getPlayerIncome(getActivePlayer()));

        if (isHolyGrailSecure() || hasEverybodyElseLost(getActivePlayer())) {
            hasGameEnded = true;
            return true;
        }
        activateNextPlayer();
        selectPlayerCastle(getActivePlayer());
        addLog(getActivePlayer() + " has " + getActivePlayer().getGold() + " gold and received " +
                world.getPlayerIncome(getActivePlayer()));

        return false;
    }

    private boolean hasEverybodyElseLost(Player currentPlayer) {
        if (player1.equals(currentPlayer)) return hasPlayerLost(player2);
        if (player2.equals(currentPlayer)) return hasPlayerLost(player1);
        return false;
    }

    private boolean hasPlayerLost(Player player) {
        return (!player.getProductionBuilding().getOwner().equals(player));
    }

    private boolean isHolyGrailSecure() {
        if (world.isUnitOnLocation(activePlayer.getProductionBuilding().getLocation())) {
            Unit selectedUnit = world.getUnitAt(activePlayer.getProductionBuilding().getLocation());
            if (selectedUnit.hasHolyGrail()) {
                return true;
            }
        }
        return false;
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
                addLog(currentUnit.pickItem());
            }
        }
    }
}
