package backend.building;

import backend.worldBuilding.Player;

public class Castle extends Building{

    public Castle(Player player){
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    @Override
    public String toString(){
        return "Castle from " + owner +" " ;
    }
}
