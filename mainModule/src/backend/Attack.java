package backend;

/**
 * Created by Julian Benitez on 10/13/2015.
 */
public class Attack{
    Integer slashDamage, piercingDamage, bluntDamage;

    public Attack(Integer slashDamage, Integer piercingDamage, Integer bluntDamage){
        this.slashDamage = slashDamage;
        this.bluntDamage = bluntDamage;
        this.piercingDamage = piercingDamage;
    }

    public Attack getModifiedAttack(Double mod){
        Attack modifiedAttack;

        modifiedAttack = new Attack((int) Math.round(slashDamage*mod),
                                    (int) Math.round(piercingDamage*mod),
                                    (int) Math.round( bluntDamage*mod));
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
