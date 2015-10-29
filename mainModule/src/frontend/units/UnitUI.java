package frontend.units;

import backend.exceptions.NoSuchPlayerException;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import frontend.EntityUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class UnitUI extends EntityUI {
    Image markerImage;

    public UnitUI(Location drawLocation, Image image, Integer ownerID, Integer cellHeight, Integer cellWidth) {
        super(drawLocation, image);
        if (ownerID == null) throw new NullArgumentException("null ownerID");
        if (cellHeight == null) throw new NullArgumentException("null cellHeight");
        if (cellWidth== null) throw new NullArgumentException("null cellWidth");

        switch (ownerID) {
            case 1:
                markerImage = new Image("file:mainModule/resources/blueMarker.png", cellWidth, cellHeight, false, false);
                break;
            case 2:
                markerImage = new Image("file:mainModule/resources/redMarker.png", cellWidth, cellHeight, false, false);
                break;
            default:
                throw new NoSuchPlayerException("No player with id " + ownerID);
        }
    }

    @Override
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(markerImage, drawLocation.getX(), drawLocation.getY());
        super.drawMe(graphicsContext);
    }
}
