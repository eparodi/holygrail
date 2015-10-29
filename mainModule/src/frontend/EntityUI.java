package frontend;

import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class EntityUI {
    protected Location drawLocation;
    protected Image image;

    /**
     *
     * @param drawLocation Entity screen coordinates
     * @param image the image to draw
     */
    public EntityUI(Location drawLocation, Image image) {
        this.drawLocation = drawLocation;
        this.image = image;
        if(drawLocation == null)throw new NullArgumentException("null drawLocation");
        if(image == null)throw new NullArgumentException("null image");
    }


    /**
     *
     * @param graphicsContext
     */
    public void drawMe(GraphicsContext graphicsContext){
        if(graphicsContext == null) throw new NullArgumentException("null graphics context");
        graphicsContext.drawImage(image,drawLocation.getX(),drawLocation.getY());
    }
}
