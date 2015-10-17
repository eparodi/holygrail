package backend.building;

import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;

public class Castle extends Building{

    public Castle(Player player){
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public Unit buildUnit(String unitType, Terrain terrain, Location location, Player player){
        Unit unit = UnitFactory.buildUnit(unitType, terrain,location,player);
        return unit;
    }

    @Override
    public String toString(){
        return "Castle from " + owner +" " ;
    }
}
