package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by Julian Benitez on 10/11/2015.
 */
public class Unit extends EntityUI {

    public Unit(Integer x,Integer y) {
        super(x,y);

    }

    public void moveTo(Location location){
        this.location = location;
    }

    @Override
    public void drawMe(GraphicsContext graphicsContext) {


        Image unit = new Image("file:knight.png", cellSize, cellSize, true, true );
        Location drawLocation = gridLocationToDrawLocation(location);

        graphicsContext.drawImage(unit, drawLocation.getX(), drawLocation.getY());

    }
}
