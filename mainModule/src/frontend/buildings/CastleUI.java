package frontend.buildings;

import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import frontend.units.UnitUI;
import javafx.scene.image.Image;

public class CastleUI extends BuildingUI {
    public CastleUI(Location drawLocation, Integer cellHeight, Integer cellWidth, Player owner) {
        super(drawLocation,new Image("file:mainModule/resources/Castle.png", cellWidth, cellHeight, false, false),
                owner, cellHeight,cellWidth);
    }
}
