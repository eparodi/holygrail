package backend.building;

import backend.worldBuilding.Player;

public class Castle {
    Player owner;

    public Castle(Player player){
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }
}
