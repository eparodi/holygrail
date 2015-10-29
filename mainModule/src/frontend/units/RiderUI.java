package frontend.units;

import backend.worldBuilding.Location;
import frontend.terrain.TerrainUI;
import javafx.scene.image.Image;

public class RiderUI extends UnitUI {
    public RiderUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Integer ownerID) {
        super(drawLocation,new Image("file:mainModule/resources/rider.png", cellWidth, cellHeight, false, false),
                ownerID, cellHeight,cellWidth);
    }
}
