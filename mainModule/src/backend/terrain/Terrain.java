package backend.terrain;

import backend.exceptions.NullArgumentException;

import java.io.Serializable;

/**
 * Represents the Terrain of a Cell, which modifies the damage of the Attacks made on the Cell, and the Action Points
 * necessary to move the unit though it.
 */
public abstract class Terrain implements Serializable {
    private static final Integer baseCost = 10;

    private Double slashBonus;
    private Double piercingBonus;
    private Double bluntBonus;

    private Integer maxSpeed;
    private Integer enduranceCost;

    /**
     * Constructs a Terrain with its additional bonuses and costs.
     * Bonuses are expressed in percentages. For example: 1.25 slash bonus.
     *
     * @param slashBonus    slash damage modifier.
     * @param piercingBonus piercing damage modifier.
     * @param bluntBonus    blunt damage modifier.
     * @param maxSpeed      max speed permitted on the terrain.
     * @param enduranceCost endurance cost.
     */
    public Terrain(Double slashBonus, Double piercingBonus, Double bluntBonus,
                   Integer maxSpeed, Integer enduranceCost) {
        this.slashBonus = slashBonus;
        this.piercingBonus = piercingBonus;
        this.bluntBonus = bluntBonus;
        this.maxSpeed = maxSpeed;
        this.enduranceCost = enduranceCost;
    }

    /**
     * Returns the Slash Damage modifier.
     *
     * @return Double value of Slash modifier.
     */
    public Double getSlashBonus() {
        return slashBonus;
    }

    /**
     * Returns the Piercing Damage modifier.
     *
     * @return Double value of Piercing modifier.
     */
    public Double getPiercingBonus() {
        return piercingBonus;
    }

    /**
     * Returns the Blunt Damage modifier.
     *
     * @return Double value of Blunt modifier.
     */
    public Double getBluntBonus() {
        return bluntBonus;
    }

    /**
     * Returns the Action Points cost to move through the terrain.
     *
     * @param unitSpeed     unit Speed.
     * @param unitEndurance unit Endurance.
     * @return Integer value with AP movement cost.
     */
    public Integer getApCost(Integer unitSpeed, Integer unitEndurance) {
        if (unitSpeed == null) throw new NullArgumentException("null unit speed");
        if (unitEndurance == null) throw new NullArgumentException("null unit endurance");

        Integer totalApCost = 0;
        Integer baseApCost = baseCost;
        while (baseApCost > 0) {
            totalApCost++;
            baseApCost -= Math.min(unitSpeed, maxSpeed) * Math.max(1, (unitEndurance - enduranceCost));
        }

        return totalApCost;
    }

    /**
     * Returns True if the Terrain can have Items.
     *
     * @return True if the Terrain can receive Items, false if not.
     */
    public boolean canReceiveItem() {
        return true;
    }
}

