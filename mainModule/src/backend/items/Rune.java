package backend.items;

public class Rune extends Item{
    private Integer slashBonus, piercingBonus, bluntBonus;

    public Rune(String name, Integer slashBonus, Integer piercingBonus, Integer bluntBonus) {
        super(name);
        this.slashBonus = slashBonus;
        this.piercingBonus = piercingBonus;
        this.bluntBonus = bluntBonus;
    }

    public String getName() {
        return name;
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

    //    }
}
