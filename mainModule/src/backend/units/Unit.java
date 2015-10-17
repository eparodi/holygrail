package backend.units;

import backend.*;
import backend.items.Armor;
import backend.items.Extra;
import backend.items.Item;
import backend.items.Rune;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;

public class Unit {
	static Integer nextId = 0;
	private Integer id;
	private String name;
	private Terrain preferredTerrain;
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
				Terrain preferredTerrain, Location location) {
		this.name = name;
		this.baseAttack = baseAttack;
		this.maxHealth = maxHealth;
		this.maxActionPoints = maxActionPoints;
		this.range = range;
		this.preferredTerrain = preferredTerrain;
		this.health = maxHealth;
		this.actionPoints = maxActionPoints;
		this.location = location;
		this.id = getNextId();
	}

	public boolean recieveDamage(Attack attack, Terrain defendersTerrain) {
		System.out.println("calcTerrainMod(defendersTerrain) = " + calcTerrainMod(defendersTerrain));
		Integer damageDealt = armor.getDamageDealt(attack, calcTerrainMod(defendersTerrain));
		health -= damageDealt;
		System.out.println("damageDealt = " + damageDealt);
		System.out.println("health = " + health);

		return health > 0;
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

	public Attack getAttack(Terrain attackerTerrain) {
		// returns the base attack with the terrain bonus the attacking unit is
		// on
		System.out.println(name + "is attacking");
		System.out.println("attackerTerrain = " + attackerTerrain);
		System.out.println("baseAttack = " + baseAttack);
		System.out.println("baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain)) = "
				+ baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain), rune));
		return baseAttack.getModifiedAttack(calcTerrainMod(attackerTerrain), rune);
	}

//	public void pickItem(Item itemPicked) {
//		if (itemPicked == null) {
//			return;//make expection
//		}
//		if (this.extra != null) {
//			dropItem(extra);
//			item = itemPicked;
//			updateStatus();
//		}
//	}
//
//	public void updateStatus() {
//		//TODO implement
//	}
//
//	public void dropItem(Item item){
//		//TODO implement
//	}

	public Integer getHealth() {
		return health;
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

	public Integer getNextId() {
		Integer aux = nextId;
		nextId++;
		return aux;
	}

}
