package backend.building;

import java.io.Serializable;

public class MineIncome implements Income,Serializable {

    @Override
    public Integer giveIncome() {
        return 6;
    }
}
