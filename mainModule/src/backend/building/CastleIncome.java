package backend.building;

import java.io.Serializable;

public class CastleIncome implements Income, Serializable {
    private static final Integer CASTLE_INCOME = 10;
    @Override
    public Integer giveIncome() {
        return CASTLE_INCOME;
    }
}
