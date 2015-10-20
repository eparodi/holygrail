package backend.units;

import backend.*;
import backend.exceptions.NullLocationException;
import backend.Defense;
import backend.items.Extra;
import backend.items.Item;
import backend.items.Rune;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

/**
 * This class represents the logic model of a unit in the game. Every unit has its owner, an Id, its type, the favorable
 * terrain, the terrain where it currently is, its position in the map, its stats ( attack, defense, health, action
 * points, range), the current status of its health and action points and two items.
 */
public class Unit {
    static Integer nextId = 0;

    Player owner;
    private Integer id;
    private UnitType unitType;
    private Terrain preferredTerrain;

    private Terrain currentTerrain;
    private Location location;

    private Attack baseAttack = null;
    private Defense defense = null;
    private Extra extra = null;
    private Rune rune = null;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;

    /**
     * Constructs a new Unit object
     * @param unitType The type of the unit.
     * @param baseAttack The base points attack of the unit.
     * @param defense The defense points of the unit.
     * @param maxHealth The maximum health of the unit.
     * @param maxActionPoints The maximum action points of the unit.
     * @param range The attack range of the unit.
     * @param currentTerrain The current Terrain of the unit.
     * @param preferredTerrain The type of Terrain where the unit takes advantage.
     * @param location The current position of the unit.
     * @param owner The player who owns the unit.
     * @see UnitFactory
     * @see UnitType
     */

    public Unit(UnitType unitType, Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints, Integer range,
                Terrain currentTerrain, Terrain preferredTerrain, Location location, Player owner) {
        this.unitType = unitType;
        this.baseAttack = baseAttack;
        this.defense = defense;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.currentTerrain = currentTerrain;
        this.preferredTerrain = preferredTerrain;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
        this.location = location;
        this.id = getNextId();
        this.owner = owner;
    }

    /**
     * Subtracts the health points of the Unit produced by the attack received, considering the unit defense.
     * @param attack The attack that was dealt by the enemy.
     */
    public void receiveDamage(Attack attack) {
        System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(currentTerrain));
        Integer damageDealt = defense.getDamageDealt(attack, calcTerrainMod(currentTerrain));
        health -= damageDealt;
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + health);
    }

    /**
     * Calculates the terrain multiplier for the attack. It depends on the terrain where the enemy is.
     * @param targetTerrain The terrain where the enemy is.
     * @return The terrain multiplier
     */
    public Double calcTerrainMod(Terrain targetTerrain) {
        // TODO This will have to be extended to use a list of terrains?
        // Maybe a ranking of preferred terrains, with the first one getting the
        // biggest mod & vice versa
        Double mod = 1D;
        if (targetTerrain.equals(preferredTerrain))
            mod = 1.5D;
        return mod;
    }

    /**
     * Returns the Attack dealt by the unit considering the terrain bonus and the base attack and the items.
     * @return the Attack dealt by the unit.
     */
    public Attack getAttack() {
        // returns the base attack with the terrain bonus the attacking unit is
        // on
        System.out.println(unitType + "is attacking");
        System.out.println("attackerTerrain = " + currentTerrain);
        System.out.println("baseAttack = " + baseAttack);
        System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = "
                + baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune));
        return baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune);
    }


    //TODO change this method.
    public void pickItem(Item itemPicked) {
        if (itemPicked == null) {
            return; //make expection
        }
        if (itemPicked.getClass().equals(Extra.class)) {
            if (this.extra != null) {
                dropItem(extra);
            }
            extra = (Extra) itemPicked;
            updateStatus();

        } else if (itemPicked.getClass().equals(Rune.class)) {
            if (this.defense != null) {
                dropItem(rune);
            }
            rune = (Rune) itemPicked;
        }
    }

    /**
     * Updates the current health and/or action points if they are being changed by a modifier.
     */
    public void updateStatus() {
        Integer newHealth = health + extra.getMaxHealthBonus();
        health = newHealth < getMaxHealth() ? newHealth : getMaxHealth();

        Integer newAP = actionPoints + extra.getMaxAPBonus();
        actionPoints = newAP < getMaxActionPoints() ? newAP : getMaxActionPoints();
    }

    //TODO implement drop
    public Item dropItem(Item item) {
        return null;
    }

    /**
     * Returns the health of the Unit.
     * @return The health of the Unit.
     */
    public Integer getHealth() {
        return health < 0 ? 0 : health;
    }

    /**
     * Returns the maximum health of the Unit, considering an item modifier.
     * @return the current maximum health of the Unit.
     */
    public Integer getMaxHealth() {
        if (extra != null) {
            return maxHealth + extra.getMaxHealthBonus();
        }
        return maxHealth;
    }

    /**
     * Returns the current action points of the Unit.
     * @return the current action points of the Unit.
     */
    public Integer getActionPoints() {
        return actionPoints;
    }

    /**
     * Returns the maximum action points of the Unit, considering an item modifier.
     * @return the current maximum action points of the Unit.
     */
    public Integer getMaxActionPoints() {
        if (extra != null) {
            return maxActionPoints + extra.getMaxAPBonus();
        }
        return maxActionPoints;
    }

    /**
     * Returns the current Location of the Unit.
     * @param location The current Location of the Unit.
     */
    public void setLocation(Location location) {
        if (location == null) throw new NullLocationException(this.toString() + " received a null location");
        this.location = location;
    }

    /**
     * Sets the current Terrain.
     * @param terrain the new current Terrain.
     */
    public void setCurrentTerrain(Terrain terrain) {
        this.currentTerrain = terrain;
    }

    /**
     * Changes the value of the current action points of the Unit to its maximum.
     */
    public void refillAP() {
        this.actionPoints = this.maxActionPoints;
    }

    /**
     * Returns the range of the Unit.
     * @return the range of the Unit.
     */
    public Integer getRange() {
        return range;
    }

    /**
     * Returns the next ID number.
     * @return the next ID number.
     */
    public Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }

    /**
     * Returns the player who owns Unit
     * @return the player who owns the Unit.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns the current Terrain of the Unit.
     * @return the current Terrain of the Unit.
     */
    public Terrain getCurrentTerrain() {
        return currentTerrain;
    }

    /**
     * Returns true if the Unit has no health or false if not.
     * @return true if the Unit has no health or false if not.
     */
    public boolean isDed() {
        return getHealth() == 0;
    }

    /**
     * Returns the position of the Unit.
     * @return the position of the Unit.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the unit type.
     * @return the unit type.
     */
    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * Subtract the action points used.
     * @param actionPointsSpent the amount of action points used.
     */
    public void spendAP(Integer actionPointsSpent) {
        actionPoints -= actionPointsSpent;
        if (actionPoints < 0) throw new IllegalStateException(this + " is using more AP than it has");
    }

    /**
     * Returns the String representation of the Unit.
     * @return the String representation of the Unit.
     */
    public String toString() {
        return getUnitType() + " " + getId();
    }

    /**
     * Returns the ID of the Unit
     * @return the ID of the Unit.
     */
    public Integer getId() {
        return id;
    }
}
