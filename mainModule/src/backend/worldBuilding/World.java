package backend.worldBuilding;

import backend.building.Building;
import backend.building.BuildingType;
import backend.building.Castle;
import backend.building.Mine;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.InvalidTerrainException;
import backend.exceptions.NullArgumentException;
import backend.items.Item;
import backend.terrain.*;
import backend.units.Unit;
import frontend.terrain.ForestUI;

import java.util.*;

public class World {
    Collection<Cell> cells;
    Collection<Unit> units;
    Collection<Building> buildings;


    Integer worldWidth, worldHeight;


    //TODO Replace player1 and player2 with Collection<Player> and receive map
    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection();
        units = new ArrayList<>();
        buildings = new ArrayList<>();


        //A PARTIR DE ACA SE CREA EL MAP DE TESTEO:
        Location player1Castle = new Location(1, Math.round(worldHeight / 2));
        Location player2Castle = new Location(worldWidth - 2, Math.round(worldHeight / 2));
        Location mineLocation = new Location(Math.round(worldWidth / 2), worldHeight - 1);

        buildings.add( new Castle(player1,player1Castle));
        buildings.add( new Castle(player2,player2Castle));
        buildings.add( new Mine(mineLocation));

        ArrayList<Cell> holyGrailPossibleCells = new ArrayList<>();

        for (Cell cell : cells) {
            if (cell.canRecieveItem()) {
                Location cellLocation = cell.getLocation();
                if (cellLocation.distance(player1Castle) > 5 && cellLocation.distance(player2Castle) > 5) {
                    if (cellLocation != mineLocation) {
                        holyGrailPossibleCells.add(cell); //TODO si hay m�s de una mina hay que cambiarlo.
                    }
                }
            }
        }

        Random random = new Random();
        int holyGrailPosition = random.nextInt(holyGrailPossibleCells.size());
        holyGrailPossibleCells.get(holyGrailPosition).addHolyGrail();
        System.out.println(holyGrailPossibleCells.get(holyGrailPosition).getLocation());

//        for (Cell cell : cells) {
//            System.out.println(cell.toString());
//        }
    }

    /**
     * Adds a Building to the Cell at the given location, with the Cell addBuilding method.
     *
     * @param building building to add.
     */
    public void addBuilding(Building building) {
        if (building == null) throw new NullArgumentException("null building parameter");

        buildings.add(building);
    }

    /**
     * Adds a Unit to the Cell at the given location, with the Cell addUnit method.
     *
     * @param unit unit to add.
     */
    public void addUnit(Unit unit) {
        if (unit == null) throw new NullArgumentException("null unit argument");
        units.add(unit);
    }

    /**
     * Removes the Unit from the Cell at the specified Location, using the Cell removeUnit method.
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
     * Adds the item of the unit, if they exist, to the Treasure Queue of the Cell.
     *
     * @param unit Unit who is dropping the items.
     * @param cell Cells where the items are being dropped.
     */
    private void dropItemToCell(Unit unit, Cell cell) {
        Item item = unit.dropRune();
        if (item != null) {
            cell.addItem(item);
        }
        item = unit.dropExtra();
        if (item != null) {
            cell.addItem(item);
        }
    }

    public boolean isUnitOnLocation(Location selectedLocation) {
        for (Unit unit : units) {
            if (unit.getLocation().equals(selectedLocation)) return true;
        }
        return false;
    }

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
     * @return a Cell type object of the specified location.
     */
    public Cell getCellAt(Location location) {
        for (Cell cell : cells) {
            if (cell.getLocation().equals(location)) return cell;
        }
        throw new CellOutOfWorldException("No cell exists at " + location.toString());
    }

    /**
     * TODO: Para qu� usamos esto?
     * Returns a Collection with all the Units in the World.
     *
     * @return a Collection of all Units.
     */
    public Collection<Unit> getUnits() {
        return units;
    }

    /**
     * TODO: Idem Arriba
     * Returns a Collection with all the Units in the World of certain Player.
     *
     * @param player owner of the Units.
     * @return a Collection of all Units from a Player.
     */
    public Collection<Unit> getUnits(Player player) {
        Collection<Unit> playerUnits = new ArrayList<>();
        for (Unit unit: units) {
            if (unit.getOwner().equals(player)) playerUnits.add(unit);
        }
        return playerUnits;
    }

    /**
     * Returns the Castle of the specified Player.
     *
     * @param player owner of the Castle.
     * @return Castle owned by Player.
     */
    //TODO (ToAsk) how to recognize castle without enum
    public Castle getPlayerCastle(Player player) {
        if (player == null) throw new NullArgumentException("player is null");
        for (Building building : buildings) {
            if (building == null) throw new NullPointerException("null building in buildings");
            if (building.getOwner() != null) {
                if (building.getOwner().equals(player)) {
                    if (building.getBuildingType().equals(BuildingType.CASTLE)) {
                        return (Castle) building;
                    }
                }
            }
        }
        return null;
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

    //TODO: change, it's only for testing terrains
    public Terrain loadTerrain(Location location) {
        if (location.getX() <= 2 && location.getY() <= 4 && location.getY() > 2) {
            return new Forest();
        }
        if (location.getX() > 6 && location.getY() <= 4 && location.getY() >= 2) {
            return new Hill();
        }
        if (location.getX() > 7 && location.getY() <= 2) {
            return new Water();
        }
        if (location.getX() <= 2 && location.getY() > 4) {
            return new Grass();
        }
        return new Grass();
    }

    /**
     * TODO: Vamos a hacer mapas espec�ficos despues?
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

    public Collection<Building> getBuildings() {
        return buildings;
    }

    public Unit getUnitAt(Location location) {
        for (Unit unit : units) {
            if (unit.getLocation().equals(location)) return unit;
        }
        throw new NoSuchElementException("No unit in units with that location: " + location);
    }
    public Building getBuildingAt(Location location) {
        for (Building building: buildings) {
            if (building.getLocation().equals(location)) return building;
        }
        throw new NoSuchElementException("No building in buildings with that location: " + location);
    }
}
