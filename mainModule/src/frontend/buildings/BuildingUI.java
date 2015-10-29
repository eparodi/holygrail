package frontend.buildings;

import backend.exceptions.NoSuchPlayerException;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import frontend.EntityUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class BuildingUI extends EntityUI {
    Image flagImage;

    public BuildingUI(Location drawLocation, Image image, Integer ownerID, Integer cellHeight, Integer cellWidth) {
        super(drawLocation, image);
        if (ownerID == null) throw new NullArgumentException("null ownerID");
        if (cellHeight == null) throw new NullArgumentException("null cellHeight");
        if (cellWidth== null) throw new NullArgumentException("null cellWidth");

        switch (ownerID) {
            case 1:
                flagImage = new Image("file:mainModule/resources/blueFlag.png", cellWidth, cellHeight, false, false);
                break;
            case 2:
                flagImage = new Image("file:mainModule/resources/redFlag.png", cellWidth, cellHeight, false, false);
                break;
            default:
                throw new NoSuchPlayerException("No player with id " + ownerID);
        }
    }

    @Override
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");
        super.drawMe(graphicsContext);
        graphicsContext.drawImage(flagImage, drawLocation.getX(), drawLocation.getY());
    }
}
