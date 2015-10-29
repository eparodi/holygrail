package frontend.buildings;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

public class MineUI extends BuildingUI {
    public MineUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Integer ownerID) {
        super(drawLocation,new Image("file:mainModule/resources/mine.png", cellWidth, cellHeight, false, false),
                ownerID, cellHeight,cellWidth);
    }
}
