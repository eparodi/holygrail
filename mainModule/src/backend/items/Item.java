package backend.items;

public class Item {
    String name="Default";

    Integer bonusAttack=0;
    Integer bonusHealth=0;
    Integer bonusActionPoints=0;

    public Item(String name, Integer bonusAttack, Integer bonusHealth, Integer bonusActionPoints){
        this.name = name;
        this.bonusAttack = bonusAttack;
        this.bonusHealth = bonusHealth;
        this.bonusActionPoints = bonusActionPoints;
    }

    public String getName() {
        return name;
    }

    public Integer getBonusHealth() {
        return bonusHealth;
    }

    public Integer getBonusActionPoints() {
        return bonusActionPoints;
    }

    public Integer getBonusAttack() {
        return bonusAttack;
    }

    public boolean isHolyGrail(){
        return name.equals("Holy Grail");
    }
}
