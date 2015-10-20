package backend;

import backend.Attack;

public class Defense {
    Integer slashResist, piercingResist, bluntResist;

    /**
     * Constructs a Defense considering three types of resistances.
     *
     * @param slashResist Slash resistance.
     * @param piercingResist Piercing resistance.
     * @param bluntResist Blunt Resistance.
     */
    public Defense(Integer slashResist, Integer piercingResist, Integer bluntResist){
        this.slashResist = slashResist;
        this.bluntResist = bluntResist;
        this.piercingResist = piercingResist;
    }

    /**
     * Returns the damage dealt by an Attack, considering the Defense.
     *
     * @param attack Attack made.
     * @param terrainMod Terrain modifier.
     * @return An Integer value with damage done by an Attack, after being applied to certain Defense.
     */
    public Integer getDamageDealt(Attack attack, Double terrainMod){
        Integer damageDealt = 0;
        damageDealt += Math.max(0,attack.getSlashDamage() - slashResist);
        damageDealt += Math.max(0,attack.getBluntDamage() - bluntResist);
        damageDealt += Math.max(0,attack.getPiercingDamage() - piercingResist);

        damageDealt =(int) Math.round(damageDealt/terrainMod);
        return damageDealt;
    }
}
