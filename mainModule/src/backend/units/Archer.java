package backend.units;

import backend.items.Armor;
import backend.Attack;
import backend.worldBuilding.Terrain;

public class Archer extends Unit {

    public Archer(Integer id) {
        super("Archer", new Attack(1,4,1), new Armor(1,3,5), 100, 10, 3, Terrain.MOUNTAIN);
    }
}
