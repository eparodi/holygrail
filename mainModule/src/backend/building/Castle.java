package backend.building;

import backend.units.Archer;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.terrain.Terrain;

public class Castle extends ProductionBuilding {

    /**
     * Constructs a Castle Building, owned by certain Player.
     *
     * @param owner Owner of the Castle.
     */
    public Castle(Player owner, Location location) {
        super(BuildingType.CASTLE,owner,new CastleIncome(),location);
    }
}
