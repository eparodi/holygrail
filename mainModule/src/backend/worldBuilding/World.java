package backend.worldBuilding;

import backend.Attack;
import backend.building.Building;
import backend.building.Castle;
import backend.building.Mine;
import backend.exceptions.CellOutOfWorldException;
import backend.exceptions.InvalidTerrainException;
import backend.exceptions.NullArgumentException;
import backend.exceptions.NullLocationException;
import backend.items.Item;
import backend.terrain.Terrain;
import backend.terrain.TerrainFactory;
import backend.units.Unit;
import frontend.CellUIData;
import sun.rmi.server.UnicastServerRef;

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

        Castle castle = new Castle(player1);
        getCellAt(player1Castle).addBuilding(castle);
        castle = new Castle(player2);
        getCellAt(player2Castle).addBuilding(castle);
        Mine mine = new Mine();
        getCellAt(mineLocation).addBuilding(mine);

        List<Cell> holyGrailPossibleCells = new ArrayList<>();

        for ( Cell cell : cells ){
            if ( cell.canRecieveItem()){
                Location cellLocation = cell.getLocation();
                if ( distance(cellLocation, player1Castle) > 5 && distance(cellLocation,player2Castle) > 5){
                    if ( cellLocation != mineLocation ){
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
     * @param location location of the cell.
     * @see Cell#addBuilding(Building)
     */
    public void addBuilding(Building building, Location location) {
        if (building == null) throw new NullArgumentException("null building parameter");
        if (location == null) throw new NullArgumentException("null location parameter");

        getCellAt(location).addBuilding(building);
    }

    /**
     * Adds a Unit to the Cell at the given location, with the Cell addUnit method.
     *
     * @param unit unit to add.
     * @see Cell#addUnit(Unit)
     */
    public void addUnit(Unit unit) {
        if (unit == null) throw new NullArgumentException("null unit argument");
        if (unit.getLocation() == null) throw new NullLocationException("unit has no location");
        getCellAt(unit.getLocation()).addUnit(unit);
    }

    /**
     * Removes the Unit from the Cell at the specified Location, using the Cell removeUnit method.
     *
     * @param location location of the cell containing the unit to be removed.
     * @see Cell#removeUnit()
     */
    public void removeUnit(Location location) {
        getCellAt(location).removeUnit();
    }

    /**
     * Removes the Unit from the Cell at his Location, using the Cell removeUnit method.
     *
     * @param unit unit to remove.
     * @see Cell#removeUnit()
     */
    public void removeUnit(Unit unit) {
        getCellAt(unit.getLocation()).removeUnit();
    }

    /**
     * Refills all Units action points of certain Player.
     *
     * @param player player whose units AP will be filled.
     */
    public void refillUnitsAP(Player player) {
        Unit unit;
        for (Cell cell : cells) {
            unit = cell.getUnit();
            if ((unit != null) && (unit.getOwner().equals(player))) unit.refillAP();
        }
    }

    /**TODO: POR QUE USA GETCELLAT.ADDUNIT Y REMOVEUNIT SI WORLD YA TIENE ESTOS METODOS?
     * Moves a Unit from the initialLocation Cell to the finalLocation Cell.
     *
     * @param initialLocation initial Location of the Unit.
     * @param finalLocation final Location of the Unit.
     * @see
     */
    public void moveUnit(Location initialLocation, Location finalLocation) {
        Unit auxUnit = getCellAt(initialLocation).getUnit();

        auxUnit.setCurrentTerrain(getTerrainAt(finalLocation));
        auxUnit.setLocation(finalLocation);

        getCellAt(initialLocation).removeUnit();
        getCellAt(finalLocation).addUnit(auxUnit);
    }

    /**
     * Captures a Building moving a Unit to the Building Location, and setting the ownership of the building to the
     * owner of the Unit.
     *
     * @param unit unit used to capture.
     * @param buildingLocation location of the Building to capture.
     */
    public void captureBuilding(Unit unit, Location buildingLocation) {
        if (unit == null) throw new NullArgumentException("null unit parameter");
        if (buildingLocation == null) throw new NullArgumentException("null location parameter");

        moveUnit(unit.getLocation(), buildingLocation);
        getCellAt(buildingLocation).getBuilding().setOwner(unit.getOwner());
    }

    /**
     * Creates an Attack from an attacker, to the defender, who receives damage of this Attack.
     *
     * @param attacker attacking Unit.
     * @param defender defending Unit.
     */
    @Deprecated
    private void attack(Unit attacker, Unit defender) {
            Attack attack = attacker.getAttack();
            defender.receiveDamage(attack);
    }

    /**
     * Battle consisted of an Attack from the attacker, and a counter-attack from the defender, if he is in range.
     *
     * @param attacker attacking Unit.
     * @param defender defending Unit.
     */
    @Deprecated
    public void skirmish(Unit attacker, Unit defender) {
        attack(attacker, defender);
        if(isInRange(defender,attacker)) {
            attack(defender, attacker);
        }
        attacker.attack(defender);
        if (attacker.isDed()){
            dropItemToCell(attacker, attackerCell);
            removeUnit(attacker);
        }

        if (defender.isDed()){
            dropItemToCell(defender, defenderCell);
            removeUnit(defender);
        }
    }

    /**
     * Adds the item of the unit, if they exist, to the Treasure Queue of the Cell.
     * @param unit Unit who is dropping the items.
     * @param cell Cells where the items are being dropped.
     */
    private void dropItemToCell(Unit unit, Cell cell){
        Item item = unit.dropRune();
        if ( item != null ){
            cell.addItem(item);
        }
        item = unit.dropExtra();
        if ( item != null ){
            cell.addItem(item);
        }
    }
    /**
     * Returns true if a Unit is in range to attack another Unit.
     *
     * @param attacker attacking Unit.
     * @param defender defending Unit.
     * @return true if the Distance between two Units is less or equals to the attacking range of the attacker Unit.
     */
    public boolean isInRange(Unit attacker, Unit defender) {
        Integer range = attacker.getRange();
        return distance(attacker.getLocation(), defender.getLocation()) <= range;
    }

    /**
     * Calculates the distance between two Cells certain locations l1 and l2.
     *
     * @param l1 location 1.
     * @param l2 location 2.
     * @return Integer value with distance between cells.
     */
    public static Integer distance(Location l1, Location l2) {
        // C�lculos raros para adaptar la matriz a la matriz de 3 ejes:
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

    @Deprecated
    /** TODO: Est� bien que este m�todo lo tenga el world, o tal vez cada Cell deber�a tener su APCost, y calcularlo segun el terrain?
     * Returns the Terrain action points cost used to move a unit through it.
     *
     * @param terrain terrain to ask cost.
     * @return Integer value of the action points cost.
     */
    public Integer getTerrainAPCost(Terrain terrain) {
        throw new InvalidTerrainException(terrain + " does not have a cost");
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
     * Returns a Collection with all the Units in the World.
     *
     * @return a Collection of all Units.
     */
    public Collection<Unit> getUnits() {
        return units;
    }

    /**TODO: Idem Arriba
     * Returns a Collection with all the Units in the World of certain Player.
     * @param player owner of the Units.
     * @return a Collection of all Units from a Player.
     */
    public Collection<Unit> getUnits(Player player) {
        Collection<Unit> units = new ArrayList<>();
        Unit unit;

        for (Cell cell : cells) {
            unit = cell.getUnit();
            if ((!(unit == null)) && unit.getOwner().equals(player)) units.add(unit);
        }

        return units;
    }

    /** TODO: Si buscamos un Castle, o una Mine, no te va a devolver solo 1?? Ya que siempre va a buscar en el mismo orden de cells, y va a retornar la location del primero que encuentre
     * Returns the Locations of certain Building.
     *
     * @param building building to search.
     * @return Location of the Building.
     */
    public Location getBuildingLocation(Building building) {
        Location location;
        if (building == null) throw new NullArgumentException("building is null");
        for (Cell cell : cells) {
            if (building.equals(cell.getBuilding())) return cell.getLocation();
        }
        //No such building exists if execution reached this point
        return null;
    }

    /**
     * Returns the Castle of the specified Player.
     *
     * @param player owner of the Castle.
     * @return Castle owned by Player.
     */
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

    /**
     * Returns the Terrain of the Cell in certain Location.
     *
     * @param location location of the Cell.
     * @return Terrain of the Cell in Location.
     */
    public Terrain getTerrainAt(Location location) {
        return getCellAt(location).terrain;
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
        if (location.getX() <= 2 && location.getY() <= 4 && location.getY() > 2){
            return TerrainFactory.buildForestTerrain();
        }
        if (location.getX() > 6 && location.getY() <= 4 && location.getY() >= 2){
            return TerrainFactory.buildHillTerrain();
        }
        if (location.getX() > 7 && location.getY() <= 2){
            return TerrainFactory.buildWaterTerrain();
        }
        if (location.getX() <= 2 && location.getY() > 4){
            return TerrainFactory.buildGrassTerrain();
        }
        return TerrainFactory.buildGrassTerrain();
    }

    /**TODO: Vamos a hacer mapas espec�ficos despues?
     * Generates all the Cells of the World.
     *
     * @return Collection of Cells.
     */
    private Collection<Cell> generateCellCollection() {

        Collection<Cell> cellCollection = new ArrayList<>();
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
     * TODO: Otro metodo que utiliza una clase de Frontend.
     * @return
     */
    public Collection<CellUIData> generateCellUIData() {
        Collection<CellUIData> cellUIDataCollection = new ArrayList<>();
        for (Cell cell : cells) {
                cellUIDataCollection.add(cell.getCellUIData());
        }
        return cellUIDataCollection;
    }

    /**
     * Returns the Player gold income.
     * @param player player whose income is asked.
     * @return Integer value with gold income.
     */
    public Integer getPlayerIncome(Player player){
        Integer income=0;
        if(player == null) {
            throw new NullArgumentException("player is null");
        }
        for(Cell cell:cells){
            if(cell.hasBuilding()){
                if(cell.getBuilding().getOwner() != null){
                    if(cell.getBuilding().getOwner().equals(player)){
                        income+=cell.getBuilding().getIncome();
                    }
                }
            }
        }
        return income;
    }

    public Collection<Building> getBuildings() {
        return buildings;
    }

    public Unit getUnitAt(Location location) {
        for (Unit unit: units){
            if(unit.getLocation().equals(location))return unit;
        }
        throw new NoSuchElementException("No unit in units with that location: " + location);
    }
}
