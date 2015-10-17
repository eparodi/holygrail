package backend.building;

import backend.worldBuilding.Player;

public abstract class Building {

    Player owner = null;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
