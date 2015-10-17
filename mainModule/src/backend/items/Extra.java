package backend.items;

public class Extra extends Item {
    Integer maxAPBonus, maxHealthBonus;
    int lala;

    public Extra(String name, Integer maxAPBonus, Integer maxHealthBonus){
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
