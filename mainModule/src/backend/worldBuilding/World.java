package backend.worldBuilding;

import backend.building.*;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.NullArgumentException;
import backend.exceptions.NullLocationException;
import backend.items.Item;
import backend.terrain.*;
import backend.units.Unit;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the game World, with all the Cells, Units and Buildings.
 */
public class World implements Serializable {
    Collection<Cell> cells;
    Collection<Unit> units;
    Collection<Building> buildings;

    Integer worldWidth, worldHeight;
    //TODO: Javadoc
    //TODO: Replace player1 and player2 with Collection<Player> and receive map
    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {
        cells = new ArrayList<>();
        units = new HashSet<>();
        buildings = new ArrayList<Building>();

        initialize(worldWidth,worldHeight,player1,player2);
    }

    private void addGrailToCell(Location player1Castle, Location player2Castle){
        ArrayList<Cell> holyGrailPossibleCells = new ArrayList<Cell>();

        for (Cell cell : cells) {
            if (cell.canRecieveItem()) {
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
        System.out.println(holyGrailPossibleCells.get(holyGrailPosition).getLocation());
    }

    /**
     * Adds a Unit to World.
     *
     * @param unit unit to add.
     */
    public void addUnit(Unit unit) {//TODO: Meter checkeo de bounds en una funcion
        if (unit == null) throw new NullArgumentException("null unit argument");
        if(unit.getLocation().getX() < 0 || unit.getLocation().getX() >= worldWidth ||
                unit.getLocation().getY() < 0 || unit.getLocation().getY() >= worldHeight)
            throw new CellOutOfWorldException("unit is out of world");
        units.add(unit);
    }

    //in the future new buildings might be added
    public void addBuilding(Building building) {
        if (building == null) throw new NullArgumentException("null unit argument");
        if(building.getLocation().getX() < 0 || building.getLocation().getX() >= worldWidth ||
                building.getLocation().getY() < 0 || building.getLocation().getY() >= worldHeight)
            throw new CellOutOfWorldException("building is out of world");
        buildings.add(building);
    }
    public void addBuilding(ProductionBuilding productionBuilding) {
        if (productionBuilding == null) throw new NullArgumentException("null unit argument");
        if(productionBuilding.getLocation().getX() < 0 || productionBuilding.getLocation().getX() >= worldWidth ||
                productionBuilding.getLocation().getY() < 0 || productionBuilding.getLocation().getY() >= worldHeight)
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
     * Returns the Castle of the specified Player.
     *
     * @param player owner of the Castle.
     * @return Castle owned by Player.
     */
    public ProductionBuilding getPlayerProductionBuilding(Player player) {
        if (player == null) {
            throw new NullArgumentException("player is null");
        }
        return player.getProductionBuilding();
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

    // TODO: Javadoc
    private Terrain loadTerrain(Location location) {
        if (location.getX() >= 5 && location.getX() < 10 && location.getY() > 3 && location.getY() <= 8
                || (location.getY() == 3 && (location.getX() == 8 || location.getX() == 9))
                || (location.getY() == 2 && (location.getX()==9 || location.getX()==10))) {
            return new Water();
        }
        if ((location.getY()<4 && location.getX()>8)) {
            return new Forest();
        }
        if ((location.getX() == 0 && location.getY() <= 7) || (location.getX() < 6 && location.getY() == 0)
                || (location.getX() == 1 && location.getY() < 3) || (location.getX()==1 &&(location.getY()==6 || location.getY()==7))
                || (location.getX()>10 && location.getY()==10)|| (location.getX()==15 && location.getY()>3)
                || (location.getX()>=10 && location.getY()==9 && location.getX()!=14 && location.getX()!=13)) {
            return new Mountain();
        }
        if ((location.getX() < 5 && location.getX()>=0 &&  location.getY() <= 10 && location.getY() > 5)
                || (location.getX()==2 && location.getY()==6) || (location.getX()==2 && location.getY()==7)) {
            return new Hill();
        }
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
     * @return Collection of all the Buildings.
     */
    public Collection<Building> getBuildings() {
        return buildings;
    }

    /**
     * Returns the Unit at a specific Location.
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
     * @param location location of the Building.
     * @return Building at the specified Location.
     */
    public Building getBuildingAt(Location location) {
        for (Building building : buildings) {
            if (building.getLocation().equals(location)) return building;
        }
        throw new NoSuchElementException("No building in buildings with that location: " + location);
    }

//TODO: Javadoc
    private void initialize(Integer worldWidth, Integer worldHeight, Player player1, Player player2){
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection();
        Location player1CastleLocation = new Location(2, 2);
        Location player2CastleLocation = new Location(14, 9);
        Location mineLocation = new Location(1,8);
        Location mineLocation2 = new Location(14,0);
        Location mineLocation3 = new Location(6,3);
        Location mineLocation4 = new Location(10,6);

        Castle player1Castle = new Castle(player1, player1CastleLocation);
        Castle player2Castle = new Castle(player2, player2CastleLocation);
        buildings.add(player1Castle);
        buildings.add(player2Castle);
        buildings.add(new Mine(mineLocation));
        buildings.add(new Mine(mineLocation2));
        buildings.add(new Mine(mineLocation3));
        buildings.add(new Mine(mineLocation4));
        addGrailToCell(player1CastleLocation,player2CastleLocation);
        player1.addProductionBuilding(player1Castle);
        player2.addProductionBuilding(player2Castle);
    }
}
