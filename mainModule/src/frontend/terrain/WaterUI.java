package frontend.terrain;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class WaterUI extends TerrainUI {
    public WaterUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation,new Image("file:mainModule/resources/cellWater.png", cellWidth, cellHeight, false, false));
    }
}
