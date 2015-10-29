package backend.terrain;

public class Water extends Terrain {
    public Water(){
        super(TerrainType.WATER,2.0,2.2,1.9,1,5);

    }
    @Override
    public boolean canRecieveItem(){
        return false;
    }
}
