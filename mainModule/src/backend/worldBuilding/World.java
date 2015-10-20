package backend.worldBuilding;

import backend.Attack;
import backend.building.Building;
import backend.building.Castle;
import backend.building.Mine;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.InvalidTerrainException;
import backend.exceptions.NullArgumentException;
import backend.exceptions.NullLocationException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import frontend.CellUIData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class World {
    Collection<Cell> cells;
    Integer worldWidth, worldHeight;


    //TODO Replace player1 and player2 with Collection<Player> and receive map
    public World(Integer worldWidth, Integer worldHeight, Player player1, Player player2) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;

        cells = generateCellCollection();

        Location player1Castle = new Location(1, Math.round(worldHeight / 2));
        Location player2Castle = new Location(worldWidth - 2, Math.round(worldHeight / 2));
        Location mineLocation = new Location(Math.round(worldWidth / 2), 0);

        Castle castle = new Castle(player1);
        getCellAt(player1Castle).addBuilding(castle);
        castle = new Castle(player2);
        getCellAt(player2Castle).addBuilding(castle);
        Mine mine = new Mine(20);
        getCellAt(mineLocation).addBuilding(mine);

//        for (Cell cell : cells) {
//            System.out.println(cell.toString());
//        }
    }

    public void addBuilding(Building building, Location location) {
        if (building == null) throw new NullArgumentException("null building parameter");
        if (location == null) throw new NullArgumentException("null location parameter");

        getCellAt(location).addBuilding(building);
    }

    public void addUnit(Unit unit) {
        if (unit == null) throw new NullArgumentException("null unit argument");
        if (unit.getLocation() == null) throw new NullLocationException("unit has no location");
        getCellAt(unit.getLocation()).addUnit(unit);
    }

    public void removeUnit(Location location) {
        getCellAt(location).removeUnit();
    }

    public void removeUnit(Unit unit) {
        getCellAt(unit.getLocation()).removeUnit();
    }

    public void refillUnitsAP(Player player) {
        Unit unit;
        for (Cell cell : cells) {
            unit = cell.getUnit();
            if ((unit != null) && (unit.getOwner().equals(player))) unit.refillAP();
        }
    }

    public void moveUnit(Location initialLocation, Location finalLocation) {
        Unit auxUnit = getCellAt(initialLocation).getUnit();

        auxUnit.setCurrentTerrain(getTerrainAt(finalLocation));
        auxUnit.setLocation(finalLocation);

        getCellAt(initialLocation).removeUnit();
        getCellAt(finalLocation).addUnit(auxUnit);
    }

    public void captureBuilding(Unit unit, Location buildingLocation) {
        if (unit == null) throw new NullArgumentException("null unit parameter");
        if (buildingLocation == null) throw new NullArgumentException("null location parameter");

        moveUnit(unit.getLocation(), buildingLocation);
        getCellAt(buildingLocation).getBuilding().setOwner(unit.getOwner());
    }

    private void attack(Unit attacker, Unit defender) {
            Attack attack = attacker.getAttack();
            defender.receiveDamage(attack);
    }

    public void skirmish(Unit attacker, Unit defender) {
        attack(attacker, defender);
        if(isInRange(defender,attacker)) {
            attack(defender, attacker);
        }

        if (attacker.isDed()) removeUnit(attacker);
        if (defender.isDed()) removeUnit(defender);
    }


    public boolean isInRange(Unit attacker, Unit defender) {
        Integer range = attacker.getRange();
        return distance(attacker.getLocation(), defender.getLocation()) <= range;
    }

    public static Integer distance(Location l1, Location l2) {
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

        return Math.max(Math.max(deltaX, deltaY), deltaZ);
    }

    public Integer getTerrainAPCost(Terrain terrain) {
        switch (terrain) {
            case GRASS:
                return 1;
            case HILL:
                return 3;
            case FOREST:
                return 2;
            //TODO (ToAsk) esta bien si water tiene costo alto o hacemos celdas no pisables?
            case WATER:
                return 20;
            case MOUNTAIN:
                return 20;
        }
        throw new InvalidTerrainException(terrain + " does not have a cost");
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

    public Location getBuildingLocation(Building building) {
        Location location;
        if (building == null) throw new NullArgumentException("building is null");
        for (Cell cell : cells) {
            if (building.equals(cell.getBuilding())) return cell.getLocation();
        }
        //No such building exists if execution reached this point
        return null;
    }

    public Castle getPlayerCastle(Player player) {
        for (Cell cell : cells) {
            if (cell.getBuilding() != null && cell.getBuilding().getOwner() != null) {
                if (cell.getBuilding().getOwner().equals(player)) {
                    if (cell.getBuilding().getBuildingType().equals("Castle")){
                        return (Castle) cell.getBuilding();
                    }
                }
            }
        }
        return null;
    }

    public Terrain getTerrainAt(Location location) {
        return getCellAt(location).terrain;
    }

    public Integer getWorldHeight() {
        return worldHeight;
    }

    public Integer getWorldWidth() {
        return worldWidth;
    }

    public Collection<Cell> getCells() {
        return cells;
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

    public Collection<CellUIData> generateCellUIData(Cell seletedCell) {
        Collection<CellUIData> cellUIDataCollection = new ArrayList<CellUIData>();
        for (Cell cell : cells) {
                cellUIDataCollection.add(cell.getCellUIData(cell.equals(seletedCell)));
        }
        return cellUIDataCollection;
    }

    public Integer getPlayerIncome(Player player){
        Integer income=0;
        for(Cell cell:cells){
            if(cell.hasBuilding()){
                if(cell.getBuilding().getOwner().equals(player)){
                    income+=cell.getBuilding().getPerTurnGoldIncome();
                }
            }
        }
        return income;
    }
}
