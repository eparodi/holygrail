package frontend.units;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class ArcherUI extends UnitUI {
    public ArcherUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Integer ownerID) {
        super(drawLocation,new Image("file:mainModule/resources/archer.png", cellWidth, cellHeight, false, false),
                ownerID, cellHeight,cellWidth);
    }
}
