package backend.units;

import backend.Attack;
import backend.exceptions.NullNameException;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;


public class UnitFactory {
    public UnitFactory(){

    }

    public Unit buildUnit(String unitName){
        Unit unit= new Unit("as",new Attack(1,2,2),2,4,6, Terrain.FOREST,new Location(2,23));
        if(unitName == null) throw new NullNameException("Null unit name");

        if(unitName.equalsIgnoreCase(""))

        return unit;
        return unit;
    }
}
