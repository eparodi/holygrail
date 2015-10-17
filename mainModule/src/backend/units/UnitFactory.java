package backend.units;

import backend.Attack;
import backend.exceptions.NoSuchUnitType;
import backend.exceptions.NullNameException;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;


public class UnitFactory {
    public UnitFactory(){

    }

    public static Unit buildUnit(String unitName, Terrain currentTerrain, Location location, Player player){

        Unit unit = null;
        if(unitName == null) throw new NullNameException("Null unit name");

        if(unitName.equalsIgnoreCase("Archer")){
            unit = new Unit("Archer",new Attack(1,4,1),7,7,2, Terrain.FOREST,currentTerrain,location, player);
//            unit.pickItem(ItemFactory.make)
        }

        if(unitName.equalsIgnoreCase("Lancer")){
            unit = new Unit("Lancer",new Attack(2,2,7),20,10,1, Terrain.GRASS,currentTerrain,location, player);
        }

        if(unit == null) throw new NoSuchUnitType("No unit type called '" + unitName +"'");
        return unit;
    }
}
