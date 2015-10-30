package frontend;

import backend.building.Building;
import backend.exceptions.NoSuchPlayerException;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import frontend.EntityUI;
import frontend.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class BuildingUI {
    private Location drawLocation;
    private ImageManager imageManager = new ImageManager();
    private Image buildingImage;
    Image flagImage;

    public BuildingUI(Location drawLocation, Building building, Integer cellHeight, Integer cellWidth) {
        if (drawLocation == null) throw new NullArgumentException("null drawLocation");
        if (building == null) throw new NullArgumentException("null building");
        if (cellHeight == null) throw new NullArgumentException("null cellHeight");
        if (cellWidth == null) throw new NullArgumentException("null cellWidth");

        this.drawLocation = drawLocation;
        this.buildingImage = new Image(imageManager.getBuildingImage(building), cellWidth, cellHeight, false, false);
        this.flagImage = new Image(imageManager.getFlagImage(building), cellWidth, cellHeight, false, false);


    }

    public void drawMe(GraphicsContext graphicsContext) {
        if (graphicsContext == null) throw new NullArgumentException("null graphics context");

        graphicsContext.drawImage(buildingImage, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(flagImage, drawLocation.getX(), drawLocation.getY());

    }
}
