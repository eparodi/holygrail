package backend.building;

import java.io.Serializable;

public class CastleIncome implements Income, Serializable {

    @Override
    public Integer giveIncome() {
        return 10;
    }
}
