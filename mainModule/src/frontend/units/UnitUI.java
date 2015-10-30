package frontend.units;

import backend.exceptions.NoSuchPlayerException;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import frontend.EntityUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class UnitUI extends EntityUI {
    Image markerImage;
    Image lifeImage;

    public UnitUI(Location drawLocation, Image image, Integer ownerID, Double lifeRatio, Integer cellHeight, Integer cellWidth) {
        super(drawLocation, image);
        if (ownerID == null) throw new NullArgumentException("null ownerID");
        if (cellHeight == null) throw new NullArgumentException("null cellHeight");
        if (cellWidth == null) throw new NullArgumentException("null cellWidth");

        //TODO: Divide into classes
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

        //TODO: fix this, put all un a map in a new class (view photo in Whatsapp group)
        if (lifeRatio == 1d) {
            lifeImage = new Image("file:mainModule/resources/life100.png", cellWidth, cellHeight, false, false);
        } else if (lifeRatio >= 0.8d) {
            lifeImage = new Image("file:mainModule/resources/life80.png", cellWidth, cellHeight, false, false);
        } else if (lifeRatio >= 0.6d) {
            lifeImage = new Image("file:mainModule/resources/life60.png", cellWidth, cellHeight, false, false);
        } else if (lifeRatio >= 0.4d) {
            lifeImage = new Image("file:mainModule/resources/life40.png", cellWidth, cellHeight, false, false);
        } else if (lifeRatio >= 0.2d) {
            lifeImage = new Image("file:mainModule/resources/life20.png", cellWidth, cellHeight, false, false);
        } else {
            lifeImage = new Image("file:mainModule/resources/lifeMin.png", cellWidth, cellHeight, false, false);
        }
    }

    @Override
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(markerImage, drawLocation.getX(), drawLocation.getY());
        super.drawMe(graphicsContext);
        graphicsContext.drawImage(lifeImage, drawLocation.getX(), drawLocation.getY());
    }
}
