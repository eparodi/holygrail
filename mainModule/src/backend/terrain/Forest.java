package backend.terrain;

public class Forest extends Terrain {
    public static final TerrainType terrainType = TerrainType.FOREST;
    public Forest(){
        super(TerrainType.FOREST,1.5,0.8,0.96,3,2);
    }
}
