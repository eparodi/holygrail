package backend.terrain;

import java.io.Serializable;

public class Forest extends Terrain implements Serializable {
    public static final TerrainType terrainType = TerrainType.FOREST;
    public Forest(){
        super(TerrainType.FOREST,1.5,0.8,0.96,3,2);
    }
}
