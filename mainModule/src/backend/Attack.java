package backend;

import java.util.Collection;

public class Attack{
    Integer slashDamage, piercingDamage, bluntDamage;

    public Attack(Integer slashDamage, Integer piercingDamage, Integer bluntDamage){
        this.slashDamage = slashDamage;
        this.bluntDamage = bluntDamage;
        this.piercingDamage = piercingDamage;
    }

    public Attack getModifiedAttack(Double terrainMod,Rune rune){
        Attack modifiedAttack;
        //TODO make item factory
        if(rune == null){
            rune = new Rune("Rune default",1,1,1);
        }
        modifiedAttack = new Attack((int) Math.round((slashDamage+rune.getSlashBonus())*terrainMod),
                                    (int) Math.round((piercingDamage+rune.getPiercingBonus())*terrainMod),
                                    (int) Math.round(( bluntDamage+rune.getBluntBonus())*terrainMod));
        return modifiedAttack;
    }

    public Integer getBluntDamage() {
        return bluntDamage;
    }

    public Integer getPiercingDamage() {
        return piercingDamage;
    }

    public Integer getSlashDamage() {
        return slashDamage;
    }

    @Override
    public String toString() {
        return "sla: " + slashDamage + " pier: " + piercingDamage + " blu: " + bluntDamage;
    }
}
