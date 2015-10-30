package backend.terrain;

import java.io.Serializable;

public class Water extends Terrain implements Serializable {
    public Water(){
        super(TerrainType.WATER,2.0,2.2,1.9,1,5);

    }
    @Override
    public boolean canRecieveItem(){
        return false;
    }
}
