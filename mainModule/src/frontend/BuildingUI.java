package frontend;

import backend.building.Building;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents the Graphical content of a Building.
 */
public class BuildingUI {
    private Location drawLocation;
    private ImageManager imageManager = new ImageManager();
    private Image buildingImage;
    private Image flagImage;

    /**
     * Constructs the UI view for a Building
     *
     * @param drawLocation Pixel based Location of the Cell.
     * @param building Building to be drawn.
     * @param cellHeight Height of the Cell in pixels.
     * @param cellWidth Width of the Cell in pixels.
     */
    public BuildingUI(Location drawLocation, Building building, Integer cellHeight, Integer cellWidth) {
        if (drawLocation == null) {
            throw new NullArgumentException("null drawLocation");
        }
        if (building == null) {
            throw new NullArgumentException("null building");
        }
        if (cellHeight == null) {
            throw new NullArgumentException("null cellHeight");
        }
        if (cellWidth == null) throw new NullArgumentException("null cellWidth");

        this.drawLocation = drawLocation;
        this.buildingImage = new Image(imageManager.getBuildingImage(building), cellWidth, cellHeight, false, false);
        this.flagImage = new Image(imageManager.getFlagImage(building), cellWidth, cellHeight, false, false);


    }

    /**
     * Draws the Building.
     *
     * @param graphicsContext Graphic Context where the Image will be drawn.
     */
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) {
            throw new NullArgumentException("null graphics context");
        }

        graphicsContext.drawImage(buildingImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(flagImage, drawLocation.getX(), drawLocation.getY());

    }
}
