package backend.worldBuilding;

import backend.Attack;
import backend.building.Castle;
import backend.exceptions.CellOutOfWorldException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class World {
    Collection<Cell> cells;
    Integer worldWidth, worldHeight;

    //TODO Replace player1 and player2 with Collection<Player> and recieve map
    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection();

        Location player1Castle = new Location(1, Math.round(worldHeight / 2));
        Location player2Castle = new Location(worldWidth - 1, Math.round(worldHeight / 2));

        Castle castle = new Castle(player1);
        getCellAt(player1Castle).addBuilding(castle);
        castle = new Castle(player2);
        getCellAt(player2Castle).addBuilding(castle);

        for (Cell cell : cells) {
            System.out.println(cell.toString());
        }
    }

    public void addUnit(Unit unit, Location location) {
        getCellAt(location).addUnit(unit);
    }

    public void removeUnit(Location location) {
        getCellAt(location).removeUnit();
    }

    public void removeUnit(Unit unit) {
        getCellAt(unit.getLocation()).removeUnit();
    }


    public void moveUnit(Location initialLocation, Location finalLocation) {
        Unit auxUnit = getCellAt(initialLocation).getUnit();

        auxUnit.setCurrentTerrain(getTerrainAt(finalLocation));
        auxUnit.setLocation(finalLocation);

        getCellAt(initialLocation).removeUnit();
        getCellAt(finalLocation).addUnit(auxUnit);
    }

    private boolean attack(Unit attacker, Unit defender) {
        if (isInRange(attacker, defender)) {
            Attack attack = attacker.getAttack();
            defender.recieveDamage(attack);
            return true;
        }
        return false;
    }

    public void skirmish(Unit attacker, Unit defender) {
        attack(attacker, defender);
        if (!defender.isDed()) {
            attack(defender, attacker);
        } else removeUnit(defender);

        if (attacker.isDed()) removeUnit(attacker);
        if (defender.isDed()) removeUnit(defender);
    }

    private boolean isInRange(Unit attacker, Unit defender) {
        Integer range = attacker.getRange();
        return distance(attacker.getLocation(), defender.getLocation()) <= range;
    }

    private Integer distance(Location l1, Location l2) {
        // Cálculos raros para adaptar la matriz a la matriz de 3 ejes:
        Integer x1 = -l1.getY();
        Integer x2 = -l2.getY();
        Integer y1 = l1.getY() % 2 == 0 ? l1.getX() + l1.getY() / 2 : l1.getX() + (l1.getY() + 1) / 2;
        Integer y2 = l2.getY() % 2 == 0 ? l2.getX() + l2.getY() / 2 : l2.getX() + (l2.getY() + 1) / 2;
        Integer z1 = -x1 - y1;
        Integer z2 = -x2 - y2;

        Integer deltaX = Math.abs(x1 - x2);
        Integer deltaY = Math.abs(y1 - y2);
        Integer deltaZ = Math.abs(z1 - z2);

        System.out.println("distance: " + Math.max(Math.max(deltaX, deltaY), deltaZ));

        return Math.max(Math.max(deltaX, deltaY), deltaZ);
    }

    public Cell getCellAt(Location location) {
        for (Cell cell : cells) {
            if (cell.getLocation().equals(location)) return cell;
        }
        throw new CellOutOfWorldException("No cell exists at " + location.toString());
    }

    public Collection<Unit> getUnits() {
        Collection<Unit> units = new ArrayList<Unit>();
        Unit unit;

        for (Cell cell : cells) {
            unit = cell.getUnit();
            if (!(unit == null)) units.add(unit);
        }
        return units;
    }

    public Collection<Unit> getUnits(Player player) {
        Collection<Unit> units = new ArrayList<Unit>();
        Unit unit;

        for (Cell cell : cells) {
            unit = cell.getUnit();
            if ((!(unit == null)) && unit.getOwner().equals(player)) units.add(unit);
        }

        return units;
    }

    public Terrain getTerrainAt(Location location) {
        return getCellAt(location).terrain;
    }

    public Terrain loadTerrain(Location location) {
        return Terrain.GRASS;
    }

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
}
