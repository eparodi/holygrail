package backend.building;

import java.io.Serializable;

/**
 * Represents the Gold Income for the Mine Building.
 */
public class MineIncome implements Income, Serializable {

    private static final Integer MINE_INCOME = 6;

    /**
     * Returns the Mine gold income.
     *
     * @return Integer value with gold income.
     */
    @Override
    public Integer giveIncome() {
        return MINE_INCOME;
    }
}
