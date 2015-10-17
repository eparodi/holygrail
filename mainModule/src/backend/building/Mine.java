package backend.building;

import backend.worldBuilding.Player;

public class Mine extends Building{
    Integer income;

    public Mine(Integer gold){
        income = gold;
        buildingType = "Mine";
    }

    public Integer getIncome(){
        return income;
    }
}
