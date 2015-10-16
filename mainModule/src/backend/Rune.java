package backend;

public class Rune {
    private String name;
    private Integer slashBonus, piercingBonus, bluntBonus;
//
//    public Rune(String name, Integer slashBonus, Integer piercingBonus, Integer bluntBonus){
//        th
//

    public Rune(String name, Integer slashBonus, Integer piercingBonus, Integer bluntBonus) {
        this.name = name;
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
