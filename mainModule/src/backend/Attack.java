package backend;

import backend.items.Item;

public class Attack{
    Integer slashDamage, piercingDamage, bluntDamage;

    /**
     * Creates an Attack with three different types of damage.
     *
     * @param slashDamage Slash damage value.
     * @param piercingDamage Piercing damage value.
     * @param bluntDamage Blunt damage value.
     */
    public Attack(Integer slashDamage, Integer piercingDamage, Integer bluntDamage){
        this.slashDamage = slashDamage;
        this.bluntDamage = bluntDamage;
        this.piercingDamage = piercingDamage;
    }

    /**
     * Returns an Attack modified by a Rune, which gives bonus damage on certain types, and a Terrain, which has a
     * damage modifier for every Unit.
     *
     * @param terrainMod Terrain modifier.
     * @param rune item.
     * @return Attack with 3 types of damage.
     */
    public Attack getModifiedAttack(Double terrainMod,Item rune){
        Attack modifiedAttack;

        if(rune == null){
            modifiedAttack = new Attack((int) Math.round(slashDamage*terrainMod),
                                        (int) Math.round(piercingDamage*terrainMod),
                                        (int) Math.round(bluntDamage*terrainMod));
        }else {
                    modifiedAttack = new Attack((int) Math.round((slashDamage + rune.getSlashBonus()) * terrainMod),
                    (int) Math.round((piercingDamage + rune.getPiercingBonus()) * terrainMod),
                    (int) Math.round((bluntDamage + rune.getBluntBonus()) * terrainMod));
        }
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
