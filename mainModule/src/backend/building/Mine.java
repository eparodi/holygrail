package backend.building;

import backend.worldBuilding.Player;

public class Mine {
    Player owner = null;
    Integer income;

    public Mine(Integer gold){
        income = gold;
    }

    public Integer getIncome(){
        return income;
    }

    public Player getOwner() {
        return owner;
    }

}
