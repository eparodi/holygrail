package frontend.units;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ArcherUI extends UnitUI {
    public ArcherUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Integer ownerID, Double lifeRatio) {
        super(drawLocation, new Image("file:mainModule/resources/archer.png", cellWidth, cellHeight, false, false),
                ownerID, lifeRatio, cellHeight, cellWidth);
    }
}
