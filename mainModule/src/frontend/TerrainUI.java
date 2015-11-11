package frontend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TerrainUI {
    protected Location drawLocation;
    protected Image image;
    protected ImageManager imageManager = new ImageManager();

    public TerrainUI(Location drawLocation, Cell cell, Integer cellHeight, Integer cellWidth) {
//        super(drawLocation, new Image(imageManager.getTerrainImage(cell.getTerrain()), cellWidth, cellHeight, false, false));
        this.drawLocation = drawLocation;
        this.image = new Image(imageManager.getTerrainImage(cell.getTerrain()), cellWidth, cellHeight, false, false);
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
    }

    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }
}
