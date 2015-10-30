package backend.units;

import backend.*;
import backend.exceptions.NullLocationException;
import backend.Defense;
import backend.items.Item;
import backend.items.ItemType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;

public class Unit {
    static Integer nextId = 0;

    private Player owner;
    private Integer id;
    private UnitType unitType;
    private Terrain preferredTerrain;

    private World world;
    private Location location;

    private Attack baseAttack = null;
    private Defense defense = null;

    /*
    private Extra extra = null;
    private Rune rune = null;
    */

    private Item extra = null;
    private Item rune = null;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;

    public Unit(UnitType unitType, Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints, Integer range,
                World world, Terrain preferredTerrain, Location location, Player owner) {
        this.unitType = unitType;
        this.baseAttack = baseAttack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.world = world;
        this.preferredTerrain = preferredTerrain;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
        this.location = location;
        this.id = getNextId();
        this.owner = owner;
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
                + baseAttack.getModifiedAttack(calcTerrainMod(getCurrentTerrain()), rune));
        return baseAttack.getModifiedAttack(calcTerrainMod(getCurrentTerrain()), rune);
    }

    public Terrain getCurrentTerrain() {
        return world.getTerrainAt(location);
    }

    public Integer getId() {
        return id;
    }

    //TODO (ToAsk) Unit should verify if he can move or is game that will verify (I think Unit should do it)
    public void move(Location finalLocation) {
        this.location = finalLocation;
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
        System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(getCurrentTerrain()));
        Integer damageDealt = defense.getDamageDealt(attack, calcTerrainMod(getCurrentTerrain()));
        health -= damageDealt;
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + health);
    }

    /**
     * Calculates the Terrain modifier for the Unit stats.
     *
     * @param targetTerrain Terrain where the Unit is standing.
     * @return Double value with the Terrain modifier.
     */
    //TODO implement new terrain modifier method
    @Deprecated
    public Double calcTerrainMod(Terrain targetTerrain) {
        // This will have to be extended to use a list of terrains?
        // Maybe a ranking of prefered terrains, with the first one getting the
        // biggest mod & viceversa
        Double mod = 1D;
        if (targetTerrain.equals(preferredTerrain))
            mod = 1.5D;
        return mod;
    }

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

}
