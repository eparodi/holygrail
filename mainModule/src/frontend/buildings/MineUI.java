package frontend.buildings;

import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import javafx.print.PageLayout;
import javafx.scene.image.Image;

public class MineUI extends BuildingUI {
    public MineUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Player owner) {
        super(drawLocation,new Image("file:mainModule/resources/mine.png", cellWidth, cellHeight, false, false),
                owner, cellHeight,cellWidth);
    }
}
