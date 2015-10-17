package backend.worldBuilding;

import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import java.util.Set;

public class World {
    Set<Cell> celdas;
    public World(int filas,int columnas){
        for(int x=1; x<=filas;x++){
            for(int y=1;y<=columnas;y++){
                celdas.add(new Cell(new Location(x,y)));
            }
        }
    }
}
