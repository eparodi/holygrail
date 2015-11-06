package backend.building;

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
     * //TODO: Un production building debería si o si tener income?
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
     * @param world
     * @return
     */
    public boolean canBuild(World world) {
        if (world.isUnitOnLocation(getLocation())) {
            return false;
        }
        if (!getOwner().canPay(UNIT_COST)) {
            return false;
        }
        return true;
    }

    /**
     * Produces an Archer Unit.
     *
     * @param world World where the Archer is created.
     */
    public void buildArcher(World world) {
        getOwner().pay(UNIT_COST);
        new Archer(world, getLocation(), getOwner());
    }

    /**
     * Produces a Rider Unit.
     *
     * @param world World where the Rider is created.
     */
    public void buildRider(World world) {
        getOwner().pay(UNIT_COST);
        new Rider(world, getLocation(), getOwner());
    }

    /**
     * Produces a Lancer Unit.
     *
     * @param world World where the Lancer is created.
     */
    public void buildLancer(World world) {
        getOwner().pay(UNIT_COST);
        new Lancer(world, getLocation(), getOwner());
    }
}
