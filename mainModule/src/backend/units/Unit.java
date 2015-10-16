package backend.units;

import backend.*;
import backend.items.Armor;
import backend.items.Item;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;

public abstract class Unit {
    private String name;
    private Terrain preferedTerrain;
    private Location location;

    private Attack baseAttack;
    private Armor armor;
    private Item item;

    private Integer health;
    private Integer maxHealth;
    private Integer actionPoints;
    private Integer maxActionPoints;
    private Integer range;

    public Unit(String name, Attack baseAttack,Armor armor ,Integer maxHealth, Integer maxActionPoints, Integer range,
                Terrain preferedTerrain){
        this.name = name;
        this.baseAttack = baseAttack;
        this.maxHealth = maxHealth;
        this.maxActionPoints = maxActionPoints;
        this.range = range;
        this.preferedTerrain = preferedTerrain;
        this.armor = armor;
        this.health = maxHealth;
        this.actionPoints = maxActionPoints;
    }
    public Unit(String name, Attack baseAttack,Armor armor ,Integer maxHealth, Integer health,
                Integer maxActionPoints, Integer actionPoints,
                Integer range, Terrain preferedTerrain){
        this(name,baseAttack,armor,maxHealth,maxActionPoints,range,preferedTerrain);
        this.health = health;
        this.actionPoints = actionPoints;
    }

    public boolean recieveDamage(Attack attack, Terrain defendersTerrain){
        System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(defendersTerrain));
        Integer damageDealt = armor.getDamageDealt(attack,calcTerrainMod(defendersTerrain));
        health -= damageDealt;
        System.out.println("damageDealt = " + damageDealt);
        System.out.println("health = " + health);

        return health>0;
    }

    public Double calcTerrainMod(Terrain targetTerrain){
        //This will have to be extended to use a list of terrains?
        //Maybe a ranking of prefered terrains, with the first one getting the biggest mod & viceversa
        Double mod=1D;
        if(targetTerrain.equals(preferedTerrain)) mod = 1.5D;
        return mod;
    }

    public Attack getAttack(Terrain attackerTerrain) {
        //returns the base attack with the terrain bonus the attacking unit is on
        System.out.println(name + "is attacking");
        System.out.println("attackerTerrain = " + attackerTerrain);
        System.out.println("baseAttack = " + baseAttack);
        System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = " +
                            baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain),null));
        return baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain),null);
    }
    public Integer getHealth() {
        return health;
    }
    public Integer getMaxHealth() {
        return maxHealth;
    }
    public Integer getActionPoints() {
        return actionPoints;
    }
    public Integer getMaxActionPoints() {
        return maxActionPoints;
    }
    public Integer getRange() {
        return range;
    }



}
