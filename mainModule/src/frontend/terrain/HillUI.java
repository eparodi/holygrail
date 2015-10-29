package frontend.terrain;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class HillUI extends TerrainUI {
    public HillUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation,new Image("file:mainModule/resources/cellHill.png", cellWidth, cellHeight, false, false));
    }
}
