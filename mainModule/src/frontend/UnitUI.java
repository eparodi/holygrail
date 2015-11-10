package frontend;

import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class UnitUI {
    private Location drawLocation;
    private Image unitImage;
    private Image markerImage;
    private Image lifeImage;
    private Image APImage;
    private ImageManager imageManager = new ImageManager();

    public UnitUI(Location drawLocation, Unit unit, Integer cellHeight, Integer cellWidth) {
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
        if (unit == null) throw new NullArgumentException("null unit");
        if (cellHeight == null) throw new NullArgumentException("null cellHeight");
        if (cellWidth == null) throw new NullArgumentException("null cellWidth");

        this.drawLocation = drawLocation;
        unitImage = new Image(imageManager.getUnitImage(unit), cellWidth, cellHeight, false, false);
        markerImage = new Image(imageManager.getMarkerImage(unit), cellWidth, cellHeight, false, false);
        lifeImage = new Image(imageManager.getLifeImage(unit), cellWidth, cellHeight, false, false);
        APImage = new Image(imageManager.getAPImage(unit), cellWidth, cellHeight, false, false);

    }

    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(markerImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(unitImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(APImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(lifeImage, drawLocation.getX(), drawLocation.getY());
    }
}
