package frontend.terrain;

import backend.worldBuilding.Location;
import frontend.EntityUI;
import javafx.scene.image.Image;

/**
 * Created by Julian Benitez on 10/29/2015.
 */
public abstract class TerrainUI extends EntityUI {
    public TerrainUI(Location drawLocation, Image image) {
        super(drawLocation, image);
    }
}
