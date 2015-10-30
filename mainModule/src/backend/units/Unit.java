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

import java.io.Serializable;

public class Unit implements Serializable {
    public static final Integer ATTACK_AP_COST = 2;
    public static final Integer DIG_AP_COST = 1;
    static Integer nextId = 0;

    private Player owner;
    private Integer id;
    private UnitType unitType;

    private World world;
    private Location location;

    private Attack baseAttack = null;
    private Defense defense = null;

    private Item extra = null;
    private Item rune = null;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;
    private Integer endurance;
    private Integer speed;


    public Unit(UnitType unitType, Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints,
                Integer range, World world, Location location, Player owner, Integer endurance, Integer speed) {
        this.unitType = unitType;
        this.baseAttack = baseAttack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.world = world;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
        this.location = location;
        this.id = getNextId();
        this.owner = owner;
        this.endurance = endurance;
        this.speed = speed;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) throw new NullLocationException(this.toString() + " received a null location");
        this.location = location;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public Integer getHealth() {
        return health < 0 ? 0 : health;
    }

    public Integer getMaxHealth() {
        if (extra != null) {
            return maxHealth + extra.getMaxHealthBonus();
        }
        return maxHealth;
    }

    public Integer getActionPoints() {
        return actionPoints;
    }

    public Integer getMaxActionPoints() {
        if (extra != null) {
            return maxActionPoints + extra.getMaxAPBonus();
        }
        return maxActionPoints;
    }

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
                + baseAttack.getModifiedAttack(getCurrentTerrain(), rune));
        return baseAttack.getModifiedAttack(getCurrentTerrain(), rune);
    }


    public Terrain getCurrentTerrain() {
        return world.getTerrainAt(location);
    }

    public Integer getId() {
        return id;
    }

    public boolean move(Location finalLocation) {
        if(finalLocation == null) throw new NullArgumentException("null final position");
        if(world.isUnitOnLocation(finalLocation)) return false;
        Integer cost =world.getTerrainAt(finalLocation).getApCost(speed,endurance);
        if(cost > actionPoints) return false;
        if(location.distance(finalLocation) != 1) return false;

        spendAP(cost);
        this.location = finalLocation;
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
        if(isDed()) world.removeUnit(this);
    }
    /**
     * Returns true if a Unit is in range to attack another Unit.
     *
     * @param unit defending Unit.
     * @return true if the Distance between two Units is less or equals to the attacking range of the attacker Unit.
     */
    public boolean isInRange(Unit unit) {
        return unit.getLocation().distance(unit.getLocation()) <= range;
    }

    public boolean attack(Unit unit) {
        if(actionPoints >= ATTACK_AP_COST){
            if(isInRange(unit)) unit.receiveDamage(this.getAttack());
            unit.counterAttack(this);
            spendAP(ATTACK_AP_COST);
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
     *
     * @param itemPicked The new item.
     * @return the Item dropped.
     */
    //TODO REHACER
    public Item pickItem(Item itemPicked) {

        Item droppedItem = null;

        if (itemPicked.getType() == ItemType.EXTRA) {
            droppedItem = this.extra;
            this.extra = itemPicked;
            updateStatus();
        } else if (itemPicked.getType() == ItemType.RUNE) {
            droppedItem = this.rune;
            this.rune = itemPicked;
        }

        return droppedItem;
    }

    /**
     * Updates the Health and Action Points, adding the bonuses from Items.
     */
    public void updateStatus() {
        Integer newHealth = health + extra.getMaxHealthBonus();
        health = newHealth < getMaxHealth() ? newHealth : getMaxHealth();

        Integer newAP = actionPoints + extra.getMaxAPBonus();
        actionPoints = newAP < getMaxActionPoints() ? newAP : getMaxActionPoints();
    }

    /**
     * Makes the Unit drop its rune.
     *
     * @return Dropped Rune.
     */
    public Item dropRune() {
        Item droppedRune = this.rune;
        this.rune = null;
        return droppedRune;
    }

    /**
     * Makes the Unit drop its Extra.
     *
     * @return Dropped Extra.
     */
    public Item dropExtra() {
        Item droppedExtra = this.extra;
        this.extra = null;
        return droppedExtra;
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
}
