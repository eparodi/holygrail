package frontend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represent the exterior border Graphics of a Selected Cell.
 */
public class SelectedCellUI {
    private Location drawLocation;
    private Image image;

    /**
     * Creates a UI for the Selected Cell view.
     *
     * @param drawLocation Pixel based Location of the Cell.
     * @param cellHeight Height of the Cell in pixels.
     * @param cellWidth Width of the Cell in pixels.
     */
    public SelectedCellUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        this.drawLocation = drawLocation;
        this.image = new Image("file:mainModule/resources/cellSelected.png", cellWidth, cellHeight, false, false);
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
    }

    /**
     * Draws the border of the Selected Cell.
     *
     * @param graphicsContext Graphic Context where the Image will be drawn.
     */
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) {
            throw new NullArgumentException("null graphics context");
        }
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }
}
