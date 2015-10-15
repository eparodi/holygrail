package backend.units;

import backend.Armor;
import backend.Attack;
import backend.terrains.Mountain;

/**
 * Created by Julian Benitez on 10/13/2015.
 */
public class Archer extends Unit {

    public Archer(Integer id) {
        super("Archer", new Attack(1,4,1), new Armor(1,3,5), 100, 10, 3,new Mountain());
    }
}
