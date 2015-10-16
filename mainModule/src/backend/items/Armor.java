package backend.items;

import backend.Attack;

public class Armor{
    Integer slashResist, piercingResist, bluntResist;

    public Armor(Integer slashResist, Integer piercingResist, Integer bluntResist){
        this.slashResist = slashResist;
        this.bluntResist = bluntResist;
        this.piercingResist = piercingResist;
    }

    public Integer getDamageDealt(Attack attack, Double terrainMod){
        Integer damageDealt = 0;
        damageDealt += Math.max(0,attack.getSlashDamage() - slashResist);
        damageDealt += Math.max(0,attack.getBluntDamage() - bluntResist);
        damageDealt += Math.max(0,attack.getPiercingDamage() - piercingResist);

        damageDealt =(int) Math.round(damageDealt/terrainMod);
        return damageDealt;
    }
}
