package frontend;

import backend.worldBuilding.Location;
import javafx.scene.image.Image;

/**
 * Created by Julian Benitez on 10/29/2015.
 */
public class SelectedCellUI extends EntityUI {
    public SelectedCellUI(Location drawLocation, Integer cellHeight, Integer cellWidth) {
        super(drawLocation, new Image("file:mainModule/resources/cellSelected.png", cellWidth, cellHeight, false, false));
    }
}
