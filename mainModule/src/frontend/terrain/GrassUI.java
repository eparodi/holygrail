package frontend.terrain;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class GrassUI extends TerrainUI {
    public GrassUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation,new Image("file:mainModule/resources/cellGrass.png", cellWidth, cellHeight, false, false));
    }
}
