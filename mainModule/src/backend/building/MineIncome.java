package backend.building;

import java.io.Serializable;

public class MineIncome implements Income,Serializable {
    private static final Integer MINE_INCOME = 6;

    @Override
    public Integer giveIncome() {
        return MINE_INCOME;
    }
}
