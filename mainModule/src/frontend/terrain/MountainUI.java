package frontend.terrain;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class MountainUI extends TerrainUI {
    public MountainUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation,new Image("file:mainModule/resources/cellMountain.png", cellWidth, cellHeight, false, false));
    }
}
