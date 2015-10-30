package backend.units;

import backend.*;
import backend.exceptions.NullArgumentException;
import backend.exceptions.NullLocationException;
import backend.Defense;
import backend.items.Item;
import backend.items.ItemType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;
import backend.worldBuilding.World;
import backend.worldBuilding.Cell;

import java.io.Serializable;
import java.util.LinkedList;

public class Unit extends Entity implements Serializable {
    public static final Integer ATTACK_AP_COST = 2;
    public static final Integer DIG_AP_COST = 1;
    static Integer nextId = 0;

    private Player owner;
    private Integer id;
    private UnitType unitType;

    private World world;
    //private Location location;

    private Attack baseAttack = null;
    private Defense defense = null;

    private LinkedList<Item> itemSlots = new LinkedList<>();
    private final int SLOT_NUMBER = 2 ;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;
    private Integer endurance;
    private Integer speed;


    public Unit(UnitType unitType, Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints,
                Integer range, World world, Location location, Player owner, Integer endurance, Integer speed) {
        super(location);
        this.unitType = unitType;
        this.baseAttack = baseAttack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.world = world;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
        //this.location = location;
        this.id = getNextId();
        this.owner = owner;
        this.endurance = endurance;
        this.speed = speed;
    }


    public UnitType getUnitType() {
        return unitType;
    }

    public Integer getHealth() {
        return health < 0 ? 0 : health;
    }

    public Integer getMaxHealth() {
        int maxHealth = this.maxHealth;
        for ( Item i : itemSlots ){
            maxHealth += i.getMaxHealthBonus();
        }
        return maxHealth;
    }

    public Integer getMaxActionPoints() {
        int maxActionPoints = this.maxActionPoints;
        for ( Item i : itemSlots ){
            maxActionPoints += i.getMaxAPBonus();
        }
        return maxActionPoints;
    }

    //TODO: nobody?
    public Integer getRange() {
        return range;
    }

    public Player getOwner() {
        return owner;
    }

    /**TODO: Sacar Printf's.
     * Returns an Attack dealt by the Unit, considering his Runes, and the Terrain modifier.
     *
     * @return modified Attack.
     */
    public Attack getAttack() {
        // returns the base attack with the terrain bonus the attacking unit is
        // on
        System.out.println(unitType + "is attacking");
        System.out.println("attackerTerrain = " + getCurrentTerrain());
        System.out.println("baseAttack = " + baseAttack);
        System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = "
                + baseAttack.getModifiedAttack(getCurrentTerrain(), itemSlots));
        return baseAttack.getModifiedAttack(getCurrentTerrain(), itemSlots);
    }


    public Terrain getCurrentTerrain() {
        return world.getTerrainAt(getLocation());
    }

    public Integer getId() {
        return id;
    }

    public boolean move(Location finalLocation) {
        if(finalLocation == null) throw new NullArgumentException("null final position");
        if(world.isUnitOnLocation(finalLocation)) return false;
        Integer cost =world.getTerrainAt(finalLocation).getApCost(speed,endurance);
        if(cost > actionPoints) return false;
        if(getLocation().distance(finalLocation) != 1) return false;

        spendAP(cost);
        setLocation(finalLocation);
        return true;
    }

    /**
     * Returns true if the unit has died.
     *
     * @return true if the unit is dead, false if it's alive.
     */
    public boolean isDed() {
        return getHealth() == 0;
    }

    /**
     * TODO: Remover Printf's
     * Makes the unit receive an Attack.
     *
     * @param attack Attack to receive.
     */
    public void receiveDamage(Attack attack) {
        Integer damageDealt = defense.getDamageDealt(attack, getCurrentTerrain());
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + health);
        health -= damageDealt;
        if(isDed()){
            dropItems();
            world.removeUnit(this);
        }
    }
	
    /**
     * Returns true if a Unit is in range to attack another Unit.
     *
     * @param unit defending Unit.
     * @return true if the Distance between two Units is less or equals to the attacking range of the attacker Unit.
     */
    public boolean isInRange(Unit unit) {
        return unit.getLocation().distance(getLocation()) <= range;
    }

    public boolean attack(Unit unit) {
        if(actionPoints >= ATTACK_AP_COST){
            if(isInRange(unit)){
                spendAP(ATTACK_AP_COST);
                unit.receiveDamage(this.getAttack());
                unit.counterAttack(this);
            }
            return true;
        }
        return false;
    }

    public void counterAttack(Unit unit){
        if(isInRange(unit)) unit.receiveDamage(this.getAttack());
    }

    /**
     * Calculates the Terrain modifier for the Unit stats.
     *
     * @param targetTerrain Terrain where the Unit is standing.
     * @return Double value with the Terrain modifier.
     */
    //TODO implement new terrain modifier method

    /**
     * Picks and item. If the unit already has an item in the slot, the item is dropped and returned.
     */
    //TODO REHACER
    public void pickItem(){

        Item droppedItem = null;
        if ( actionPoints >= DIG_AP_COST ){
            actionPoints -= DIG_AP_COST;
            Cell dugCell = world.getCellAt(getLocation());
            Item itemPicked = dugCell.getItem();
            if ( itemPicked != null){
                System.out.println("Item: " + itemPicked.getName());
                if ( itemSlots.size() == SLOT_NUMBER ){
                    droppedItem = itemSlots.remove();
                }
                itemSlots.add(itemPicked);
                updateStatus(itemPicked);
                dugCell.addItem(droppedItem);
            }
        }
    }

    /**
     * Updates the Health and Action Points, adding the bonuses from Items.
     */
    public void updateStatus( Item item ) {
        Integer newHealth = health + item.getMaxHealthBonus();
        health = newHealth < getMaxHealth() ? newHealth : getMaxHealth();

        Integer newAP = actionPoints + item.getMaxAPBonus();
        actionPoints = newAP < getMaxActionPoints() ? newAP : getMaxActionPoints();
    }

    /**
     * Makes the Unit drop its Items.
     */
    public void dropItems() {
        Cell currentCell = world.getCellAt(getLocation());
        for ( Item i : itemSlots ){
            currentCell.addItem(i);
        }
    }


    /**
     * Refills the Unit Action Points.
     */
    public void refillAP() {
        this.actionPoints = this.maxActionPoints;
    }

    /**
     * Spends an amount of the Unit Action Points.
     *
     * @param actionPointsSpent ap spent.
     */
    public void spendAP(Integer actionPointsSpent) {
        actionPoints -= actionPointsSpent;
        if (actionPoints < 0) throw new IllegalStateException(this + " is using more AP than it has");
    }

    public String toString() {
        return getUnitType() + " " + getId();
    }

    public static Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        return unit.getId().equals( this.getId());

    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    public boolean hasHolyGrail(){
        for ( Item i : itemSlots ){
            if ( i.isHolyGrail() ){
                return true;
            }
        }
        return false;
    }

}
