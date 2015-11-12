package backend.building;

import java.io.Serializable;

/**
 * Represents the Gold Income for the Castle Building.
 */
public class CastleIncome implements Income, Serializable {

    private static final Integer CASTLE_INCOME = 5;

    /**
     * Returns the Castle gold income.
     *
     * @return Integer value with gold income.
     */
    @Override
    public Integer giveIncome() {
        return CASTLE_INCOME;
    }
}
