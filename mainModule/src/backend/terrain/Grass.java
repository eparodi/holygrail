package backend.terrain;

import java.io.Serializable;

/**
 * Represents Grass Terrain of a Cell.
 */
public class Grass extends Terrain implements Serializable {
    public Grass(){
        super(1.2,1.0,1.4,5,1); //High speed and low endurance cost
    }
}
