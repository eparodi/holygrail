package backend.items;

import java.io.Serializable;

public class Item implements Serializable {
	private String name = "Default";
    private ItemType type;
    private Integer maxAPBonus = 0, maxHealthBonus = 0; //for Extra Type
    private Integer slashBonus = 0, piercingBonus = 0, bluntBonus = 0; //for Rune Type

	public Item(String name , ItemType type , Integer maxAPBonus, Integer maxHealthBonus, Integer slashBonus,
                Integer piercingBonus, Integer bluntBonus) {
		this.name = name;
        this.type = type;
        this.maxAPBonus = maxAPBonus;
        this.maxHealthBonus = maxHealthBonus;
        this.slashBonus = slashBonus;
        this.piercingBonus = piercingBonus;
        this.bluntBonus = bluntBonus;
	}

	public String getName() {
		return name;
	}

    public ItemType getType() {
        return type;
    }

	public boolean isHolyGrail() {
		return name.equals("Holy Grail");
	}

    public Integer getSlashBonus() {
        return slashBonus;
    }

    public Integer getPiercingBonus() {
        return piercingBonus;
    }

    public Integer getBluntBonus() {
        return bluntBonus;
    }

    public Integer getMaxAPBonus() {
        return maxAPBonus;
    }

    public Integer getMaxHealthBonus() {
        return maxHealthBonus;
    }
}
