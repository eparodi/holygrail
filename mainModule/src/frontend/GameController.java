package frontend;

import backend.Game;
import backend.exceptions.*;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Collection;

public class GameController {
    Integer cellHeight;
    Integer cellWidth;
    Integer worldHeight;
    Integer worldWidth;

    //    /**
//     * @param cellHeight    The height of the cell in pixels
//     * @param cellWidth     The width of the cell in pixels
//     */
    public GameController(Integer worldHeight, Integer worldWidth, GraphicsContext graphicsContext) {
        this.worldHeight = worldHeight;
        this.worldWidth = worldWidth;
        //TODO: cálculos turbio que rompen todo
//        cellWidth = (int) graphicsContext.getCanvas().getWidth() / worldWidth;
//        cellHeight = (int) graphicsContext.getCanvas().getHeight() / worldHeight;
        cellHeight = 100;
        cellWidth = 100;
    }

    public Integer getCellHeight(){
        return cellHeight;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void SelectCell(Game game, Location location) {
        game.actionAttempt(location);
    }

    public Location drawLocationToGridLocation(Integer x, Integer y) {
        Location gridLocation = new Location(0, 0);

        Double auxY = Math.floor(y / (((double) 3 / 4) * cellHeight));
        Double auxX = auxY.intValue() % 2 == 0 ? Math.floor(x / cellWidth) : Math.floor((x - cellWidth / 2) / cellWidth);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }

    public void attemptAction(Game game, double drawX, double drawY) {
        Location gridLocation = drawLocationToGridLocation((int) drawX, (int) drawY);
        if (gridLocation.getY() < worldHeight && gridLocation.getX() < worldWidth)
            game.actionAttempt(gridLocation);
    }

    public void updateGraphics(GraphicsContext graphicsContext, Collection<CellUIData> cellUIDataCollection) {
        //TODO Revisar:
//        cellWidth = (int) graphicsContext.getCanvas().getWidth() / worldWidth;
//        cellHeight = (int) graphicsContext.getCanvas().getHeight() / worldHeight;
        //clear the canvas
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        drawCells(graphicsContext, cellUIDataCollection);

    }

    public void drawCells(GraphicsContext graphicsContext, Collection<CellUIData> cellUIDataCollection) {
        for (CellUIData cellUIData : cellUIDataCollection) {
            drawTerrain(graphicsContext, cellUIData.getTerrain(), cellUIData.getLocation());

            if (cellUIData.isSelected()) {
                drawCellSelection(graphicsContext, cellUIData.getLocation());
            }

            if (cellUIData.getBuildingType() != null) {
                drawBuilding(graphicsContext, cellUIData.getBuildingType(), cellUIData.getLocation(), cellUIData.getOwner());
            }
            if (cellUIData.getUnitType() != null) {
                drawUnit(graphicsContext, cellUIData.getUnitType(), cellUIData.getLocation(), cellUIData.getOwner());
                drawLife(graphicsContext, cellUIData.getHealth(), cellUIData.getMaxHealth(), cellUIData.getLocation());
            }
        }
    }

    private void drawBuilding(GraphicsContext graphicsContext, String buildingType, Location location, Player owner) {
        Image image = getBuildingImage(buildingType);
        Image flag = getFlagImage(owner);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(flag, drawLocation.getX(), drawLocation.getY());
    }

    public void drawCellSelection(GraphicsContext graphicsContext, Location location) {
        Image image = new Image("file:mainModule/resources/cellSelected.png", cellWidth, cellHeight, false, false);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }

    public void drawLife(GraphicsContext graphicsContext, Integer health, Integer maxHealth, Location location) {
        Image image = getLifebarImage(health, maxHealth);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }

    private void drawUnit(GraphicsContext graphicsContext, UnitType unitType, Location location, Player owner) {
        Image image = getUnitImage(unitType);
        Image ownerMarker = getOwnerMarker(owner);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(ownerMarker, drawLocation.getX(), drawLocation.getY());
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }

    public void drawTerrain(GraphicsContext graphicsContext, Terrain terrain, Location location) {
        Image image = getTerrainImage(terrain);
        Location drawLocation = gridLocationToDrawLocation(location);
        graphicsContext.drawImage(image, drawLocation.getX(), drawLocation.getY());
    }

    public Location gridLocationToDrawLocation(Location gridLocation) {
        Location drawLocation = new Location(0, 0);

        drawLocation.setX(gridLocation.getY() % 2 == 0 ? gridLocation.getX() * cellWidth :
                gridLocation.getX() * cellWidth + cellWidth / 2); // Depende de fila par/impar
        drawLocation.setY(gridLocation.getY() * (cellHeight - cellHeight / 4));

        return drawLocation;
    }

    public Image getBuildingImage(String buildingType) {
        if (buildingType == null) throw new NullArgumentException("Null building type in image");
        if (buildingType.equals("Castle"))
            return new Image("file:mainModule/resources/Castle.png", cellWidth, cellHeight, false, false);
        if (buildingType.equals("Mine"))
            return new Image("file:mainModule/resources/mine.png", cellWidth, cellHeight, false, false);
        throw new RuntimeException("No image for that building type: " + buildingType);
    }

    public Image getTerrainImage(Terrain terrain) {
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

    public Image getUnitImage(UnitType unitType) {
        if (unitType == null) throw new NullArgumentException("Null unit unitName in image");
        switch (unitType) {
            case ARCHER:
                return new Image("file:mainModule/resources/archer.png", cellWidth, cellHeight, false, false);

            case LANCER:
                return new Image("file:mainModule/resources/lancer.png", cellWidth, cellHeight, false, false);

            case RIDER:
                return new Image("file:mainModule/resources/rider.png", cellWidth, cellHeight, false, false);
            default:
                throw new NoSuchUnitTypeException("No image for that unit: " + unitType);
        }
    }

    public Image getLifebarImage(Integer health, Integer maxHealth) {
        Double lifePercentage = health.doubleValue() / maxHealth.doubleValue();
        if (lifePercentage == 1.0) {
            return new Image("file:mainModule/resources/life100.png", cellWidth, cellHeight, false, false);
        } else if (lifePercentage >= 0.8 && lifePercentage < 1.0) {
            return new Image("file:mainModule/resources/life80.png", cellWidth, cellHeight, false, false);
        } else if (lifePercentage >= 0.6 && lifePercentage < 0.8) {
            return new Image("file:mainModule/resources/life60.png", cellWidth, cellHeight, false, false);
        } else if (lifePercentage >= 0.4 && lifePercentage < 0.6) {
            return new Image("file:mainModule/resources/life40.png", cellWidth, cellHeight, false, false);
        } else if (lifePercentage >= 0.2 && lifePercentage < 0.4) {
            return new Image("file:mainModule/resources/life20.png", cellWidth, cellHeight, false, false);
        } else if (lifePercentage > 0.0 && lifePercentage < 0.2) {
            return new Image("file:mainModule/resources/lifeMin.png", cellWidth, cellHeight, false, false);
        }
        throw new NoSuchLifeImageException("No image to represent health.");
    }

    public Image getOwnerMarker(Player owner){
        //TODO retorno null asi no mas?
        if (owner == null){
            return null;
        }
        if (owner.getId().equals(1)){
            return new Image("file:mainModule/resources/blueMarker.png", cellWidth, cellHeight, false, false);
        } else if (owner.getId().equals(2)){
            return new Image("file:mainModule/resources/redMarker.png", cellWidth, cellHeight, false, false);
        } else{
            throw new NoSuchPlayerException("The player " + owner.getId() + " does not exist.");
        }
    }

    public Image getFlagImage(Player owner){
        //TODO retorno null asi no mas?
        if (owner == null){
            return null;
        }
        if (owner.getId().equals(1)){
            return new Image("file:mainModule/resources/blueFlag.png", cellWidth, cellHeight, false, false);
        } else if (owner.getId().equals(2)){
            return new Image("file:mainModule/resources/redFlag.png", cellWidth, cellHeight, false, false);
        } else{
            throw new NoSuchPlayerException("The player " + owner.getId() + " does not exist.");
        }
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}