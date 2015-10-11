package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by Julian Benitez on 10/11/2015.
 */
public class Cell extends EntityUI {


    public Cell(Integer x, Integer y){
        super(x,y);

    }

    @Override
    public void drawMe(GraphicsContext graphicsContext) {
        Integer drawX, drawY;
        Image terrain = new Image("file:cellGreen.png", cellSize, cellSize, true, true );
        Location drawLocation = gridLocationToDrawLocation(location);

        graphicsContext.drawImage(terrain ,drawLocation.getX(),drawLocation.getY());
    }
}
