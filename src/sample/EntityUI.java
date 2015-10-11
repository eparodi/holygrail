package sample;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by Julian Benitez on 10/11/2015.
 */
public abstract class EntityUI {
    protected Location location;
    protected Integer cellSize = 100;

    public EntityUI(Integer x, Integer y){
        location = new Location(x,y);
    }
    public Location gridLocationToDrawLocation(Location gridLocation){
        Location drawLocation = new Location(0,0);

        drawLocation.setX(location.getY() %2 == 0? location.getX()*cellSize:location.getX()*cellSize + cellSize/2);
        drawLocation.setY(location.getY() *(cellSize - cellSize/4));

        return drawLocation;
    }
    public abstract void drawMe(GraphicsContext graphicsContext);
}
