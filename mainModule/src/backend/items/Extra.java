package backend.items;

public class Extra extends Item {
    Integer maxAPBonus, maxHealthBonus;

    public Extra(String name, Integer maxHealthBonus, Integer maxAPBonus){
        super(name);
        this.maxAPBonus = maxAPBonus;
        this.maxHealthBonus = maxHealthBonus;
    }

    public Integer getMaxAPBonus() {
        return maxAPBonus;
    }

    public Integer getMaxHealthBonus() {
        return maxHealthBonus;
    }
}
