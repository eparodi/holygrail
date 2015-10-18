package frontend;

import backend.Game;
import backend.exceptions.InvalidTerrainException;
import backend.exceptions.NoSuchUnitType;
import backend.exceptions.NullArgumentException;
import backend.worldBuilding.Location;
import backend.worldBuilding.Terrain;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.omg.CORBA.IMP_LIMIT;

import java.util.Collection;

public class GameController {
    Integer cellHeight, cellWidth, worldHeight, worldWidth;

    /**
     * @param cellHeight    The height of the cell in pixels
     * @param cellWidth     The width of the cell in pixels
     */
    public GameController(Integer cellHeight, Integer cellWidth) {
        this.cellHeight = cellHeight;
        this.cellWidth = cellWidth;
    }

    public void SelectCell(Game game, Location location){
        game.actionAttempt(location);
    }

    public Location drawLocationToGridLocation(Double x,Double y){
        Location gridLocation = new Location(0,0);

        Double auxY = Math.floor(y/75);
        Double auxX = auxY.intValue() % 2 == 0? Math.floor(x/100):Math.floor((x-50)/100);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }

    public void updateGraphics(GraphicsContext graphicsContext, Collection<CellUIData> cellUIDataCollection){
        graphicsContext.fillRect(10,100,100,100);
        drawCells(graphicsContext, cellUIDataCollection);
    }

    public void drawCells(GraphicsContext graphicsContext, Collection<CellUIData> cellUIDataCollection){
        for(CellUIData cellUIData: cellUIDataCollection){
            drawTerrain(graphicsContext,cellUIData.getTerrain(),cellUIData.getLocation());

            if(cellUIData.buildingType != null )
                drawBuilding(graphicsContext,cellUIData.buildingType,cellUIData.getLocation());

            if(cellUIData.getUnitName() != null)
                drawUnit(graphicsContext,cellUIData.getUnitName(),cellUIData.getLocation());

        }
    }

    private void drawBuilding(GraphicsContext graphicsContext, String buildingType, Location location) {
        Image image = getBuildingImage(buildingType);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image,drawLocation.getX(),drawLocation.getY());
    }
    private void drawUnit(GraphicsContext graphicsContext, String unitType, Location location){
        Image image = getUnitImage(unitType);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image,drawLocation.getX(),drawLocation.getY());
    }

    public void drawTerrain(GraphicsContext graphicsContext, Terrain terrain, Location location){
        Image image = getTerrainImage(terrain);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }

    public Location gridLocationToDrawLocation(Location gridLocation) {
        Location drawLocation = new Location(0, 0);

        drawLocation.setX(gridLocation.getY() % 2 == 0 ? gridLocation.getX() * cellWidth : gridLocation.getX() * cellWidth + cellWidth / 2); // Depende de fila par/impar
        drawLocation.setY(gridLocation.getY() * (cellHeight - cellHeight / 4));

        return drawLocation;
    }

    public Image getBuildingImage(String buildingType){
        if(buildingType == null) throw new NullArgumentException("Null building type in image");
        if(buildingType.equals("Castle"))
            return new Image("file:mainModule/resources/Castle.png", cellWidth, cellHeight, false, false);
        if(buildingType.equals("Mine"))
            return new Image("file:mainModule/resources/mine.png", cellWidth, cellHeight, false, false);
        throw new RuntimeException("No image for that building type: " + buildingType);
    }
    public Image getTerrainImage(Terrain terrain){
        switch (terrain) {
            case GRASS:
                return new Image("file:mainModule/resources/cellGrass.png", cellWidth, cellHeight, false, false);

            case MOUNTAIN:
                return new Image("file:mainModule/resources/cellMountain.png", cellWidth, cellHeight, false, false);

            case FOREST:
                return new Image("file:mainModule/resources/cellForest.png", cellWidth, cellHeight, false, false);

            case WATER:
                return new Image("file:mainModule/resources/cellWater.png", cellWidth, cellHeight, false, false);
        }
        throw new InvalidTerrainException("No image for that terrain");
    }
    public Image getUnitImage(String unitName){
        if(unitName == null) throw new NullArgumentException("Null unit unitName in image");
        if(unitName.equalsIgnoreCase("Archer"))
                return new Image("file:mainModule/resources/may.png", cellWidth, cellHeight, false, false);
        if(unitName.equalsIgnoreCase("Lancer"))
                return new Image("file:mainModule/resources/ash.png", cellWidth, cellHeight, false, false);
        throw new NoSuchUnitType("No image for that unit: " + unitName);
    }
}
