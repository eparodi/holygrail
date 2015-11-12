package backend.building;

import backend.Log;
import backend.units.*;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;

import java.io.Serializable;

/**
 * Represents a Building with the ability to produce Units.
 */
public class ProductionBuilding extends Building implements Serializable {
    public static final Integer UNIT_COST = 10;

    /**
     * Constructs a Production building at certain Location, owned by a Player.
     *
     * @param owner    player who owns the building.
     * @param income   gold income.
     * @param location location of the building.
     */
    public ProductionBuilding(Player owner, Income income, Location location) {
        super(owner, income, location);
    }

    /**
     * Returns True if the Production Building can produce a Unit, checking if the Location where the unit has to
     * be created is occupied, and if the owner has enough gold.
     *
     * @param world The World in which the building resides.
     * @return True if the a unit can be built, false if not.
     */
    public boolean canBuild(World world) {
        String msg;
        if (!world.isUnitOnLocation(getLocation())) {
            if (getOwner().canPay(UNIT_COST)) {
                return true;
            }else {
                msg = getOwner() + " needs " + UNIT_COST + " and has " + getOwner().getGold() + ".";
            }
        }else{
            msg = "There is a unit on the " + this + ", cant build.";
        }
        Log.getInstance().addLog(msg);
        return false;
    }

    /**
     * Produces an Archer Unit.
     *
     * @param world World where the Archer is created.
     */
    public void buildArcher(World world) {
        if (canBuild(world)) {
            getOwner().pay(UNIT_COST);
            new Archer(world, getLocation(), getOwner());
            Log.getInstance().addLog(this + " built an Archer, " + getOwner().getGold() + " gold left.");
        }
    }

    /**
     * Produces a Rider Unit.
     *
     * @param world World where the Rider is created.
     */
    public void buildRider(World world) {
        if (canBuild(world)) {
            getOwner().pay(UNIT_COST);
            new Rider(world, getLocation(), getOwner());
            Log.getInstance().addLog(this + " built an Rider, " + getOwner().getGold() + " gold left.");
        }
    }

    /**
     * Produces a Lancer Unit.
     *
     * @param world World where the Lancer is created.
     */
    public void buildLancer(World world) {
        if (canBuild(world)) {
            getOwner().pay(UNIT_COST);
            new Lancer(world, getLocation(), getOwner());
            Log.getInstance().addLog(this + " built an Lancer, " + getOwner().getGold() + " gold left.");
        }
    }
}
