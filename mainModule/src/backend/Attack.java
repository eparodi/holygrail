package backend;

import backend.items.Item;
import backend.terrain.Terrain;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Represents an Attack, made of different damaging types.
 */
public class Attack implements Serializable {
    private Integer slashDamage, piercingDamage, bluntDamage;

    /**
     * Creates an Attack with three different types of damage.
     *
     * @param slashDamage    Slash damage value.
     * @param piercingDamage Piercing damage value.
     * @param bluntDamage    Blunt damage value.
     */
    public Attack(Integer slashDamage, Integer piercingDamage, Integer bluntDamage) {
        this.slashDamage = slashDamage;
        this.bluntDamage = bluntDamage;
        this.piercingDamage = piercingDamage;
    }

    /**
     * Returns an Attack modified by Items, which gives bonus damage on certain types, and a Terrain, which has a
     * damage modifier for every Unit.
     *
     * @param terrain Terrain where the attack is being made.
     * @param items   List of damage modifying items.
     * @return Attack with 3 types of damage.
     */
    public Attack getModifiedAttack(Terrain terrain, LinkedList<Item> items) {
        Attack modifiedAttack;
        int slashDamage = this.slashDamage;
        int piercingDamage = this.piercingDamage;
        int bluntDamage = this.bluntDamage;

        for (Item i : items) {
            slashDamage += i.getSlashBonus();
            piercingDamage += i.getPiercingBonus();
            bluntDamage += i.getBluntBonus();
        }

        modifiedAttack = new Attack((int) Math.round(slashDamage * terrain.getSlashBonus()),
                (int) Math.round(piercingDamage * terrain.getPiercingBonus()),
                (int) Math.round(bluntDamage * terrain.getBluntBonus()));

        return modifiedAttack;
    }

    /**
     * Returns the Blunt Damage of an Attack.
     *
     * @return Integer value of Blunt Damage.
     */
    public Integer getBluntDamage() {
        return bluntDamage;
    }

    /**
     * Returns the Piercing Damage of an Attack.
     *
     * @return Integer value of Piercing Damage.
     */
    public Integer getPiercingDamage() {
        return piercingDamage;
    }

    /**
     * Returns the Slash Damage of an Attack.
     *
     * @return Integer value of Slash Damage.
     */
    public Integer getSlashDamage() {
        return slashDamage;
    }

    @Override
    public String toString() {
        return "sla: " + slashDamage + " pier: " + piercingDamage + " blu: " + bluntDamage;
    }
}
