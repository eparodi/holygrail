package backend.building;

import backend.worldBuilding.Player;

public class Mine extends Building{
    Integer income;

    public Mine(Integer gold){
        income = gold;
    }

    public Integer getIncome(){
        return income;
    }

    public void captureMine(Player player){
        owner = player;
    }
}
