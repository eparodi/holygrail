package backend;

import backend.Attack;
import backend.terrain.Terrain;

import java.io.Serializable;

/**
 * Represents the Defense of a Unit, based on different Resistance types.
 */
public class Defense implements Serializable {
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
     * @param terrain Terrain .
     * @return An Integer value with damage done by an Attack, after being applied to certain Defense.
     */
    public Integer getDamageDealt(Attack attack, Terrain terrain){
        Integer damageDealt = 0;
        damageDealt += Math.max(0,(int) (attack.getSlashDamage() - slashResist * terrain.getSlashBonus()));
        damageDealt += Math.max(0,(int) (attack.getBluntDamage() - bluntResist * terrain.getBluntBonus()));
        damageDealt += Math.max(0,(int) (attack.getPiercingDamage() - piercingResist * terrain.getPiercingBonus()));
        return damageDealt;
    }
}
