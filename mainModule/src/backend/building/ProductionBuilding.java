package backend.building;

import backend.units.*;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

public class ProductionBuilding extends Building implements Serializable {
    public static final Integer UNIT_COST = 10;

    public ProductionBuilding(BuildingType buildingType, Player owner, Income income, Location location) {
        super(buildingType, owner, income,location);
    }

    public void buildLancer(World world){
        owner.pay(UNIT_COST);
        world.addUnit(new Lancer(world,getLocation(),owner));
    }

    public boolean canBuild(World world) {
        if(world.isUnitOnLocation(getLocation())){
            return false;
        }
        if(!owner.canPay(UNIT_COST)){
            return false;
        }
        return true;
    }
    public void buildArcher(World world){
        owner.pay(UNIT_COST);
        world.addUnit(new Archer(world,getLocation(),owner));
    }
    public void buildRider(World world){
            owner.pay(UNIT_COST);
            world.addUnit(new Rider(world,getLocation(),owner));
    }
}
