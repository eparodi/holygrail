package frontend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SelectedCellUI {
    protected Location drawLocation;
    protected Image image;

    /**
     * @param drawLocation Entity screen coordinates
     * @param image        the image to draw
     */
    public SelectedCellUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        this.drawLocation = drawLocation;
        this.image = new Image("file:mainModule/resources/cellSelected.png", cellWidth, cellHeight, false, false);
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
    }

    /**
     * @param graphicsContext
     */
    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }
}
