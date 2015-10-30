package backend.terrain;

import java.io.Serializable;

public class Grass extends Terrain implements Serializable {
    public Grass(){
        super(TerrainType.GRASS,1.2,1.0,1.4,5,1); //High speed and low endurance cost
    }
}
