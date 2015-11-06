package backend.items;

import java.io.Serializable;

/**
 * Represents an Item in the Game, it can be equipped by a Unit, providing better Stats, and it can be the Holy Grail,
 * the special game item that can be delivered to the Castle to win the game.
 */
public class Item implements Serializable {
	private String name = "Default";
    private Integer maxAPBonus = 0, maxHealthBonus = 0; //for Extra Type
    private Integer slashBonus = 0, piercingBonus = 0, bluntBonus = 0; //for Rune Type

    /**
     * Constructs an Item with a name, AP bonus, Health bonus, and damage of any of the three types bonus.
     * @param name name of the Item.
     * @param maxAPBonus Action Points bonus.
     * @param maxHealthBonus Health bonus.
     * @param slashBonus Slash Damage bonus.
     * @param piercingBonus Piercing Damage bonus.
     * @param bluntBonus Blunt Damage bonus.
     */
	public Item(String name ,Integer maxAPBonus, Integer maxHealthBonus, Integer slashBonus,
                Integer piercingBonus, Integer bluntBonus) {
		this.name = name;
        this.maxAPBonus = maxAPBonus;
        this.maxHealthBonus = maxHealthBonus;
        this.slashBonus = slashBonus;
        this.piercingBonus = piercingBonus;
        this.bluntBonus = bluntBonus;
	}

    /**
     * Returns the name of the item.
     * @return String name of the item.
     */
	public String getName() {
		return name;
	}

    /**
     * Returns true if the item is the Holy Grail.
     * @return True if the item is the Holy Grail, false if not.
     */
	public boolean isHolyGrail() {
		return name.equals("Holy Grail");
	}

    /**
     * Returns the Slash damage bonus of the item.
     * @return Integer value with Slash bonus.
     */
    public Integer getSlashBonus() {
        return slashBonus;
    }

    /**
     * Returns the Piercing damage bonus of the item.
     * @return Integer value with Piercing bonus.
     */
    public Integer getPiercingBonus() {
        return piercingBonus;
    }

    /**
     * Returns the Blunt damage bonus of the item.
     * @return Integer value with Blunt bonus.
     */
    public Integer getBluntBonus() {
        return bluntBonus;
    }

    /**
     * Returns the AP Bonus of the item.
     * @return Integer value with AP bonus.
     */
    public Integer getMaxAPBonus() {
        return maxAPBonus;
    }

    /**
     * Returns the Health Bonus of the item.
     * @return Integer value with Health bonus.
     */
    public Integer getMaxHealthBonus() {
        return maxHealthBonus;
    }
}
