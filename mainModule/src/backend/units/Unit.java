package backend.units;

import backend.*;
import backend.exceptions.NullLocationException;
import backend.items.Armor;
import backend.items.Extra;
import backend.items.Item;
import backend.items.Rune;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

public class Unit {
    static Integer nextId = 0;

    Player owner;
    private Integer id;
    private String name;
    private Terrain preferredTerrain;

    private Terrain currentTerrain;
    private Location location;

    private Attack baseAttack = null;
    private Armor armor = null;
    private Extra extra = null;
    private Rune rune = null;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;

    public Unit(String name, Attack baseAttack, Integer maxHealth, Integer maxActionPoints, Integer range,
                Terrain currentTerrain, Terrain preferredTerrain, Location location, Player owner) {
        this.name = name;
        this.baseAttack = baseAttack;
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

    public void receiveDamage(Attack attack) {
        System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(currentTerrain));
        Integer damageDealt = armor.getDamageDealt(attack, calcTerrainMod(currentTerrain));
        health -= damageDealt;
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + health);
    }

    public Double calcTerrainMod(Terrain targetTerrain) {
        // This will have to be extended to use a list of terrains?
        // Maybe a ranking of prefered terrains, with the first one getting the
        // biggest mod & viceversa
        Double mod = 1D;
        if (targetTerrain.equals(preferredTerrain))
            mod = 1.5D;
        return mod;
    }

    public Attack getAttack() {
        // returns the base attack with the terrain bonus the attacking unit is
        // on
        System.out.println(name + "is attacking");
        System.out.println("attackerTerrain = " + currentTerrain);
        System.out.println("baseAttack = " + baseAttack);
        System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = "
                + baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune));
        return baseAttack.getModifiedAttack(calcTerrainMod(currentTerrain), rune);
    }

    //TODO preguntar si está bien o no este metodo
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

        } else if (itemPicked.getClass().equals(Armor.class)) {
            if (this.armor != null) {
                dropItem(armor);
            }
            armor = (Armor) itemPicked;

        } else if (itemPicked.getClass().equals(Rune.class)) {
            if (this.armor != null) {
                dropItem(rune);
            }
            rune = (Rune) itemPicked;
        }
    }

    public void updateStatus() {
        Integer newHealth = health + extra.getMaxHealthBonus();
        health = newHealth < getMaxHealth() ? newHealth : getMaxHealth();

        Integer newAP = actionPoints + extra.getMaxAPBonus();
        actionPoints = newAP < getMaxActionPoints() ? newAP : getMaxActionPoints();
    }

    public Item dropItem(Item item) {
        return null;
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

    public void setLocation(Location location) {
        if (location == null) throw new NullLocationException(this.toString() + " recieved a null location");
        this.location = location;
    }

    public void setCurrentTerrain(Terrain terrain) {
        this.currentTerrain = terrain;
    }

    public void refillAP() {
        this.actionPoints = this.maxActionPoints;
    }

    public Integer getRange() {
        return range;
    }

    public Integer getNextId() {
        Integer aux = nextId;
        nextId++;
        return aux;
    }

    public Player getOwner() {
        return owner;
    }

    public Terrain getCurrentTerrain() {
        return currentTerrain;
    }


    public boolean isDed() {
        return getHealth() == 0;
    }

    public Location getLocation() {
        return location;
    }
}
