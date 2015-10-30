package frontend.units;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ArcherUI extends UnitUI {
    public ArcherUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Integer ownerID) {
        super(drawLocation, new Image("file:mainModule/resources/archer.png", cellWidth, cellHeight, false, false),
                ownerID, cellHeight, cellWidth);
    }

    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) {
            throw new NullArgumentException("null graphics context");
        }

        graphicsContext.drawImage(markerImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }
}
