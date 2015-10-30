package backend.terrain;
@Deprecated
public abstract class TerrainFactory {
    public static Forest buildForestTerrain(){
        return new Forest();
    }

    public static Grass buildGrassTerrain(){
        return new Grass();
    }

    public static Hill buildHillTerrain(){
        return new Hill();
    }
    public static Mountain buildMountainTerrain(){
        return new Mountain();
    }
    
    public static Water buildWaterTerrain(){
        return new Water();
    }
}
