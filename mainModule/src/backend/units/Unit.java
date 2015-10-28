package backend.units;

import backend.*;
import backend.exceptions.NullLocationException;
import backend.Defense;
import backend.items.Item;
import backend.items.ItemType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

public class Unit extends UnitPredecesor{

    //Player owner;
    private UnitType unitType;
    private Terrain preferredTerrain;

    private Terrain currentTerrain;
    //private Location location;

    private Attack baseAttack = null;
    private Defense defense = null;

    /*
    private Extra extra = null;
    private Rune rune = null;
    */

    private Item extra = null;
    private Item rune = null;


    private Integer range;


    public Unit(UnitType unitType, Attack baseAttack, Defense defense, Integer maxHealth, Integer maxActionPoints, Integer range,
                Terrain currentTerrain, Terrain preferredTerrain, Location location, Player owner) {

        super(owner,location,maxActionPoints,maxHealth);
        this.unitType = unitType;
        this.baseAttack = baseAttack;
        this.defense = defense;

        this.range = range;
        this.currentTerrain = currentTerrain;
        this.preferredTerrain = preferredTerrain;

    }

    /**TODO: Remover Printf's
     * Makes the unit receive an Attack.
     *
     * @param attack Attack to receive.
     */
    public void receiveDamage(Attack attack) {
        System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(currentTerrain));
        Integer damageDealt = defense.getDamageDealt(attack, calcTerrainMod(currentTerrain));
        super.reduceHealth(damageDealt);
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + super.getHealth());
    }

    /**
     * Calculates the Terrain modifier for the Unit stats.
     *
     * @param targetTerrain Terrain where the Unit is standing.
     * @return Double value with the Terrain modifier.
     */
    public Double calcTerrainMod(Terrain targetTerrain) {
        Double mod = 1D;
        if (targetTerrain.equals(preferredTerrain))
            mod = 1.5D;
        return mod;
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
        System.out.println("attackerTerrain = " + currentTerrain);
        System.out.println("baseAttack = " + baseAttack);
        System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = "
                + baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune));
        return baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune);
    }

    /**
     * Picks and item. If the unit already has an item in the slot, the item is dropped and returned.
     * @param itemPicked The new item.
     * @return the Item dropped.
     */
    //TODO REHACER
    @Deprecated
    public Item pickItem(Item itemPicked) {

        Item droppedItem = null;

        if (itemPicked.getType() == ItemType.EXTRA){
            droppedItem = this.extra;
            this.extra = itemPicked;
            updateStatus();
        }else if (itemPicked.getType() == ItemType.RUNE){
            droppedItem = this.rune;
            this.rune = itemPicked;
        }

        return droppedItem;
    }

    /**
     * Updates the Health and Action Points, adding the bonuses from Items.
     */
    public void updateStatus() {
        Integer newHealth = super.getHealth() + extra.getMaxHealthBonus();
        reduceHealth(newHealth < getMaxHealth() ? newHealth : getMaxHealth());

        Integer newAP = getActionPoints() + extra.getMaxAPBonus();
        setActionPoints(newAP < getMaxActionPoints() ? newAP : getMaxActionPoints());
    }

    /**
     * Makes the Unit drop its rune.
     * @return Dropped Rune.
     */
    public Item dropRune() {
        Item droppedRune = this.rune;
        this.rune = null;
        return droppedRune;
    }

    /**
     * Makes the Unit drop its Extra.
     * @return Dropped Extra.
     */
    public Item dropExtra() {
        Item droppedExtra = this.extra;
        this.extra = null;
        return droppedExtra;
    }

    @Override
    public Integer getMaxHealth() {
        if (extra != null) {
            return super.getMaxHealth() + extra.getMaxHealthBonus();
        }
        return  super.getMaxHealth();
    }

    @Override
    public Integer getMaxActionPoints() {
        if (extra != null) {
            return super.getMaxActionPoints() + extra.getMaxAPBonus();
        }
        return super.getMaxActionPoints();
    }

    public void setCurrentTerrain(Terrain terrain) {
        this.currentTerrain = terrain;
    }

    public Integer getRange() {
        return range;
    }

    public Terrain getCurrentTerrain() {
        return currentTerrain;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public String toString() {
        return getUnitType() + " " + getId();
    }
}
