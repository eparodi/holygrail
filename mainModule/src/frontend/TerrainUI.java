package frontend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents the Terrain graphical content of a Cell.
 */
public class TerrainUI {
    private Location drawLocation;
    private Image image;
    private ImageManager imageManager = new ImageManager();

    /**
     * Constructs the Terrain graphical content of a Cell.
     *
     * @param drawLocation Pixel based Location of the Cell.
     * @param cell Cell to be drawn.
     * @param cellHeight Height of the Cell in pixels.
     * @param cellWidth Width of the Cell in pixels.
     */
    public TerrainUI(Location drawLocation, Cell cell, Integer cellHeight, Integer cellWidth) {
//        super(drawLocation, new Image(imageManager.getTerrainImage(cell.getTerrain()), cellWidth, cellHeight, false, false));
        this.drawLocation = drawLocation;
        this.image = new Image(imageManager.getTerrainImage(cell.getTerrain()), cellWidth, cellHeight, false, false);
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
    }

    /**
     * Draws the Terrain on the Cell.
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