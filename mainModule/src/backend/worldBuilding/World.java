package backend.worldBuilding;

import backend.building.*;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.NullArgumentException;
import backend.terrain.*;
import backend.units.Unit;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the game World, with all the Cells, Units and Buildings.
 */
public class World implements Serializable {
    private Collection<Cell> cells;
    private Collection<Unit> units;
    private Collection<Building> buildings;

    private Integer worldWidth, worldHeight;

    /**
     * Creates a World with certain Width and Height, with 2 players.
     *
     * @param worldWidth  World Width.
     * @param worldHeight World Height.
     * @param player1     Player 1.
     * @param player2     Player 2.
     */
    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {

        cells = new ArrayList<Cell>();
        units = new HashSet<Unit>();
        buildings = new ArrayList<Building>();

        initialize(worldWidth, worldHeight, player1, player2);
    }

    public void addGrailToCell(Location player1Castle, Location player2Castle) {
        ArrayList<Cell> holyGrailPossibleCells = new ArrayList<Cell>();

        for (Cell cell : cells) {
            if (cell.canReceiveItem()) {
                Location cellLocation = cell.getLocation();
                if (cellLocation.distance(player1Castle) > 5 && cellLocation.distance(player2Castle) > 5) {
                    if (!isBuildingOnLocation(cellLocation)) {
                        holyGrailPossibleCells.add(cell);
                    }
                }
            }
        }

        Random random = new Random();
        int holyGrailPosition = random.nextInt(holyGrailPossibleCells.size());
        holyGrailPossibleCells.get(holyGrailPosition).addHolyGrail();
        //para la presentacion:
        //System.out.println(holyGrailPossibleCells.get(holyGrailPosition).getLocation());
    }

    /**
     * Adds a Unit to World.
     *
     * @param unit unit to add.
     */
    public void addUnit(Unit unit) {
        if (unit == null) throw new NullArgumentException("null unit argument");
        if (!isLocationOnBounds(unit.getLocation()))
            throw new CellOutOfWorldException("unit is out of world");
        units.add(unit);
    }

    private boolean isLocationOnBounds(Location location) {
        return !(location.getX() < 0 || location.getX() >= worldWidth ||
                location.getY() < 0 || location.getY() >= worldHeight);
    }

    //in the future new buildings might be added
    public void addBuilding(Building building) {
        if (building == null) throw new NullArgumentException("null unit argument");
        if (!isLocationOnBounds(building.getLocation()))
            throw new CellOutOfWorldException("building is out of world");
        buildings.add(building);
    }

    public void addBuilding(ProductionBuilding productionBuilding) {
        if (productionBuilding == null) throw new NullArgumentException("null unit argument");
        if (!isLocationOnBounds(productionBuilding.getLocation()))
            throw new CellOutOfWorldException("building is out of world");
        buildings.add(productionBuilding);
        productionBuilding.getOwner().addProductionBuilding(productionBuilding);
    }

    /**
     * Removes the Unit from the World.
     *
     * @param unit unit to be removed.
     */
    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    /**
     * Refills all Units action points of certain Player.
     *
     * @param player player whose units AP will be filled.
     */
    public void refillUnitsAP(Player player) {
        for (Unit unit : units) {
            if (unit == null) throw new NullPointerException("null unit in units");
            if (unit.getOwner().equals(player)) unit.refillAP();
        }
    }

    /**
     * Returns true if there is a Unit on the selected Location, false if not.
     *
     * @param selectedLocation Location of the World.
     * @return True if there is a Unit on the selected Location, false if not.
     */
    public boolean isUnitOnLocation(Location selectedLocation) {
        for (Unit unit : units) {
            if (unit.getLocation().equals(selectedLocation)) return true;
        }
        return false;
    }

    /**
     * Returns true if there is a building on the selected Location, false if not.
     *
     * @param selectedLocation Location of the World.
     * @return True if there is a Building on the selected Location, false if not.
     */
    public boolean isBuildingOnLocation(Location selectedLocation) {
        for (Building building : buildings) {
            if (building.getLocation().equals(selectedLocation)) return true;
        }
        return false;
    }


    /**
     * Returns the Cell of certain Location.
     *
     * @param location location of the Cell.
     * @return the Cell of the specified location.
     */
    public Cell getCellAt(Location location) {
        for (Cell cell : cells) {
            if (cell.getLocation().equals(location)) return cell;
        }
        throw new CellOutOfWorldException("No cell exists at " + location.toString());
    }

    /**
     * Returns a Collection with all the Units in the World.
     *
     * @return a Collection of all Units.
     */
    public Collection<Unit> getUnits() {
        return units;
    }

    /**
     * Returns the Terrain of the Cell in certain Location.
     *
     * @param location location of the Cell.
     * @return Terrain of the Cell in Location.
     */
    public Terrain getTerrainAt(Location location) {
        return getCellAt(location).getTerrain();
    }

    /**
     * Returns the World height.
     *
     * @return Integer value of World height.
     */
    public Integer getWorldHeight() {
        return worldHeight;
    }

    /**
     * Returns the World width.
     *
     * @return Integer value of World width.
     */
    public Integer getWorldWidth() {
        return worldWidth;
    }

    /**
     * Returns a Collection of all Cells in the World.
     *
     * @return Collection of all Cells.
     */
    public Collection<Cell> getCells() {
        return cells;
    }

    /**
     * Returns the terrain from a location
     *
     * @param location the location from which the method returns the terrain
     *
     * @return
     */
    private Terrain loadTerrain(Location location) {
            return new Grass();
    }

    /**
     * Generates all the Cells of the World.
     *
     * @return Collection of Cells.
     */
    private Collection<Cell> generateCellCollection() {

        Collection<Cell> cellCollection = new ArrayList<Cell>();
        Cell cell;
        Location cellLocation;

        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {

                cellLocation = new Location(i, j);
                cell = new Cell(cellLocation, loadTerrain(cellLocation));

                cellCollection.add(cell);
            }
        }
        return cellCollection;
    }

    /**
     * Returns the Player gold income.
     *
     * @param player player whose income is asked.
     * @return Integer value with gold income.
     */
    public Integer getPlayerIncome(Player player) {
        Integer income = 0;
        if (player == null) {
            throw new NullArgumentException("player is null");
        }
        for (Building building : buildings) {
            if (building.getOwner() != null) {
                if (building.getOwner().equals(player)) {
                    income += building.getIncome();
                }
            }
        }

        return income;
    }

    /**
     * Returns all the Buildings in the World.
     *
     * @return Collection of all the Buildings.
     */
    public Collection<Building> getBuildings() {
        return buildings;
    }

    /**
     * Returns the Unit at a specific Location.
     *
     * @param location location of the Unit.
     * @return Unit at the specified Location.
     */
    public Unit getUnitAt(Location location) {
        for (Unit unit : units) {
            if (unit.getLocation().equals(location)) return unit;
        }
        throw new NoSuchElementException("No unit in units with that location: " + location);
    }

    /**
     * Returns the Building at a specific Location.
     *
     * @param location location of the Building.
     * @return Building at the specified Location.
     */
    public Building getBuildingAt(Location location) {
        for (Building building : buildings) {
            if (building.getLocation().equals(location)) return building;
        }
        throw new NoSuchElementException("No building in buildings with that location: " + location);
    }

    private void initialize(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        cells = generateCellCollection();

        Location player1CastleLocation = new Location(0, 5);
        Location player2CastleLocation = new Location(15, 5);

        Castle player1Castle = new Castle(player1, player1CastleLocation);
        Castle player2Castle = new Castle(player2, player2CastleLocation);

        buildings.add(player1Castle);
        buildings.add(player2Castle);

        addGrailToCell(player1CastleLocation, player2CastleLocation);
        player1.addProductionBuilding(player1Castle);
        player2.addProductionBuilding(player2Castle);
    }


}
