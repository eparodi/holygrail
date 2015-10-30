package backend.building;

import backend.units.*;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

/**
 * Created by Julian Benitez on 10/29/2015.
 */
public class ProductionBuilding extends Building {
    public static final Integer UNIT_COST = 10;

    public ProductionBuilding(BuildingType buildingType, Player owner, Income income, Location location) {
        super(buildingType, owner, income,location);
    }

    public void buildLancer(World world){
        owner.pay(UNIT_COST);
        world.addUnit(new Lancer(world,location,owner));
    }

    public boolean canBuild(World world) {
        if(world.isUnitOnLocation(location)){
            return false;
        }
        if(!owner.canPay(UNIT_COST)){
            return false;
        }
        return true;
    }
    public void buildArcher(World world){
        owner.pay(UNIT_COST);
        world.addUnit(new Archer(world,location,owner));
    }
    public void buildRider(World world){
            owner.pay(UNIT_COST);
            world.addUnit(new Rider(world,location,owner));
    }
}
