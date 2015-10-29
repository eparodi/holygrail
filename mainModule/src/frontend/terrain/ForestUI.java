package frontend.terrain;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class ForestUI extends TerrainUI {
    public ForestUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation,new Image("file:mainModule/resources/cellForest.png", cellWidth, cellHeight, false, false));
    }
}
