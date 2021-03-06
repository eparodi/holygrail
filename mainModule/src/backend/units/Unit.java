package backend.units;

import backend.*;
import backend.exceptions.NullArgumentException;
import backend.Defense;
import backend.items.Item;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;
import backend.worldBuilding.World;
import backend.worldBuilding.Cell;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Represents a Unit in the game.
 */
public abstract class Unit extends OwnableEntity implements Serializable {
    public static final Integer ATTACK_AP_COST = 2;
    public static final Integer DIG_AP_COST = 2;
    public static final Integer MAX_MOVEMENT=1;
    private static Integer nextId = 0;

    private Player owner;
    private Integer id;

    private World world;

    private Attack baseAttack = null;
    private Defense defense = null;

    private LinkedList<Item> itemSlots = new LinkedList<Item>();
    private final int SLOT_NUMBER = 2;

    private Integer health;
    private Integer maxHealth;

    private Integer actionPoints;

    private Integer maxActionPoints;
    private Integer range;
    private Integer endurance;
    private Integer speed;

    /**
     * Creates a Unit with certain stats on a specific Location in the World, with a Player owner.
     *
     * @param baseAttack      Attack of the unit.
     * @param defense         Defense of the unit.
     * @param maxHealth       Max Health of the unit.
     * @param maxActionPoints Max AP of the unit.
     * @param range           Range of the unit.
     * @param world           World the unit is in.
     * @param location        Location of the unit.
     * @param owner           Player who owns the unit.
     * @param endurance       Endurance of the unit.
     * @param speed           Speed of the unit.
     */
    public Unit(Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints,
                Integer range, World world, Location location, Player owner, Integer endurance, Integer speed) {
        super(location, owner);
        this.baseAttack = baseAttack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.world = world;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
        this.id = getNextId();
        this.owner = owner;
        this.endurance = endurance;
        this.speed = speed;
        world.addUnit(this);
    }

    /**
     * Returns the Health of the unit.
     *
     * @return Integer value of units health.
     */
    public Integer getHealth() {
        return health < 0 ? 0 : health;
    }


    /**
     * Returns the Max Health of the unit.
     *
     * @return Integer value with the units max health.
     */
    public Integer getMaxHealth() {
        int maxHealth = this.maxHealth;
        for (Item i : itemSlots) {
            maxHealth += i.getMaxHealthBonus();
        }
        return maxHealth;
    }

    /**
     * Returns the units max AP.
     *
     * @return Integer value of the units Max AP.
     */
    public Integer getMaxActionPoints() {
        int maxActionPoints = this.maxActionPoints;
        for (Item i : itemSlots) {
            maxActionPoints += i.getMaxAPBonus();
        }
        return maxActionPoints;
    }

    /**
     * Returns the Action Points of the unit.
     *
     * @return Integer value of units AP.
     */
    public Integer getActionPoints() {
        return actionPoints;
    }

    /**
     * Returns the owner of the unit.
     *
     * @return Player who owns the unit.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns an Attack dealt by the Unit, considering his Runes, and the Terrain modifier.
     *
     * @return modified Attack.
     */
    public Attack getAttack() {
        return baseAttack.getModifiedAttack(getCurrentTerrain(), itemSlots);
    }

    /**
     * Returns the units current Terrain.
     *
     * @return Terrain of the unit current Location.
     */
    public Terrain getCurrentTerrain() {
        return world.getTerrainAt(getLocation());
    }

    /**
     * Returns the player's ID.
     *
     * @return Integer value of player's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Moves the unit to the final Location, and returns true if the unit has moved.
     *
     * @param finalLocation Location to move the unit.
     * @return True if the unit has moved, false if not.
     */
    public void move(Location finalLocation) {
        StringBuilder msg =  new StringBuilder();
        if (finalLocation == null) {
            throw new NullArgumentException("null final position");
        }

        if (! world.isUnitOnLocation(finalLocation)) {
            Integer cost = world.getTerrainAt(finalLocation).getApCost(speed, endurance);
            if (cost <= actionPoints) {
                if (getLocation().distance(finalLocation) <= MAX_MOVEMENT) {
                    msg.append("Moved to " + finalLocation);
                    spendAP(cost);
                    setLocation(finalLocation);

                    if (world.isBuildingOnLocation(finalLocation)) {
                        world.getBuildingAt(finalLocation).setOwner(owner);
                        msg.append(this+ "captured building at " + finalLocation);
                    }

                }else {
                    msg.append( finalLocation + " is too far away");
                }
            }else {
                msg.append( this + "needs " + cost + " AP to move, and it has " + actionPoints + " AP.");
            }
        }else {
            msg.append( this + " cant move, cell occupied at " + finalLocation);
        }
        Log.getInstance().addLog(msg.toString());
    }

    /**
     * Returns true if the unit has an Item.
     *
     * @return True if the unit has an Item, false if not.
     */
    public boolean hasItem() {
        return !itemSlots.isEmpty();
    }

    /**
     * Returns true if the unit has died.
     *
     * @return true if the unit is dead, false if it's alive.
     */
    private boolean isDed() {
        return getHealth() == 0;
    }

    /**
     * Makes the unit receive an Attack.
     *
     * @param attack Attack to receive.
     */
    private void receiveDamage(Attack attack) {
        StringBuilder msg = new StringBuilder();

        Integer damageDealt = defense.getDamageDealt(attack, getCurrentTerrain());
        health -= damageDealt;
        msg.append(this + " recieved " + damageDealt + " damage");
        if (isDed()) {
            dropItems();
            world.removeUnit(this);
            msg.append(" and died");
        }
        msg.append(".");
        Log.getInstance().addLog(msg.toString());
    }

    /**
     * Returns true if a Unit is in range to attack another Unit.
     *
     * @param unit defending Unit.
     * @return true if the Distance between two Units is less or equals to the attacking range of the attacker Unit.
     */
    private boolean isInRange(Unit unit) {
        return unit.getLocation().distance(getLocation()) <= range;
    }

    /**
     * Makes an attack to another unit. Returns true if it attacked.
     *
     * @param unit Unit to attack.
     */
    public void attack(Unit unit) {
        StringBuilder msg = new StringBuilder();
        Log log = Log.getInstance();
        if (actionPoints >= ATTACK_AP_COST) {
            if (isInRange(unit)) {
                spendAP(ATTACK_AP_COST);
                //If we move the log.addLog(msg.toString()) to the end of the method,
                //the message logs are displayed in wrong order
                msg.append(this + " attacked " + unit + ".");
                log.addLog(msg.toString());

                unit.receiveDamage(this.getAttack());
                unit.counterAttack(this);
            }else {
                msg.append(this + " is too far away from " + unit + ".");
                log.addLog(msg.toString());
            }
        }else {
            msg.append(this + " needs " + ATTACK_AP_COST + " AP to attack and has " + actionPoints + ".");
            log.addLog(msg.toString());
        }
    }

    /**
     * Performs a counterattack to a unit. It takes damage if it's in range.
     *
     * @param unit unit to attack.
     */
    private void counterAttack(Unit unit) {
        if (isInRange(unit)) unit.receiveDamage(this.getAttack());
    }

    /**
     * Picks and item. If the unit already has an item in the slot, the item is dropped and returned.
     */
    public void pickItem() {
        Item itemPicked = null;
        Item droppedItem = null;
        String msg = "";
        if (actionPoints >= DIG_AP_COST) {
            actionPoints -= DIG_AP_COST;
            Cell dugCell = world.getCellAt(getLocation());
            itemPicked = dugCell.getItem();
            if (itemPicked != null) {
                if (itemSlots.size() == SLOT_NUMBER) {
                    droppedItem = itemSlots.remove();
                    dugCell.addItem(droppedItem);
                }
                itemSlots.add(itemPicked);
                updateStatus(itemPicked);
                msg = "An item has been found: " + itemPicked.getName();
            } else {
                msg = "No item found";
            }
        } else {
            msg = this + " needs " + DIG_AP_COST + " AP to dig and has " + actionPoints + ".";
        }
        Log.getInstance().addLog(msg);
    }

    /**
     * Updates the Health and Action Points, adding the bonuses from Items.
     */
    private void updateStatus(Item item) {
        Integer newHealth = health + item.getMaxHealthBonus();
        health = newHealth < getMaxHealth() ? newHealth : getMaxHealth();

        Integer newAP = actionPoints + item.getMaxAPBonus();
        actionPoints = newAP < getMaxActionPoints() ? newAP : getMaxActionPoints();
    }

    /**
     * Makes the Unit drop its Items.
     */
    private void dropItems() {
        Cell currentCell = world.getCellAt(getLocation());
        for (Item i : itemSlots) {
            currentCell.addItem(i);
        }
    }

    /**
     * Refills the Unit Action Points.
     */
    public void refillAP() {
        this.actionPoints = getMaxActionPoints();
    }

    /**
     * Spends an amount of the Unit Action Points.
     *
     * @param actionPointsSpent ap spent.
     */
    private void spendAP(Integer actionPointsSpent) {
        actionPoints -= actionPointsSpent;
        if (actionPoints < 0) throw new IllegalStateException(this + " is using more AP than it has");
    }

    public String toString() {
        return "Unit: " + getId();
    }

    /**
     * Returns the next available ID, to make every unit unique.
     *
     * @return Integer value of ID.
     */
    private static Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Unit unit = (Unit) o;

        return unit.getId().equals(this.getId());

    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    /**
     * Returns true if the unit has the Holy Grail in its inventory.
     *
     * @return True if the unit has the Holy Grail, false if not.
     */
    public boolean hasHolyGrail() {
        for (Item i : itemSlots) {
            if (i.isHolyGrail()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the Location of the unit.
     *
     * @param location new location of the unit.
     */
    private void setLocation(Location location) {
        this.location = location;
    }
}
