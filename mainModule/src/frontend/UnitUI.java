package frontend;

import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents the Graphical content of a Unit, its health and AP.
 */
public class UnitUI {
    private Location drawLocation;
    private Image unitImage;
    private Image markerImage;
    private Image lifeImage;
    private Image APImage;
    private ImageManager imageManager = new ImageManager();

    /**
     * Constructs the UI view for a Unit.
     *
     * @param drawLocation Pixel based Location of the Cell.
     * @param unit Unit to be drawn.
     * @param cellHeight Height of the Cell in pixels.
     * @param cellWidth Width of the Cell in pixels.
     */
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

    /**
     * Draws the unit UI.
     *
     * @param graphicsContext Graphic Context where the Image will be drawn.
     */
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(markerImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(unitImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(APImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(lifeImage, drawLocation.getX(), drawLocation.getY());
    }
}
