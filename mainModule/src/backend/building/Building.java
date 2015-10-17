package backend.building;

import backend.worldBuilding.Player;

public abstract class Building {
    String buildingType;
    Player owner = null;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getBuildingType(){
        return buildingType;
    }

    public String toString(){
        return buildingType + " from " + owner + "; ";
    }
}
