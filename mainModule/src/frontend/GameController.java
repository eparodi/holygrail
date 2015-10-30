package frontend;

import backend.Game;
import backend.building.Building;
import backend.building.BuildingType;
import backend.building.Castle;
import backend.exceptions.*;
import backend.terrain.Hill;
import backend.units.Unit;
import backend.units.UnitType;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import backend.terrain.Terrain;
import frontend.buildings.CastleUI;
import frontend.buildings.MineUI;
import frontend.terrain.*;
import frontend.units.ArcherUI;
import frontend.units.LancerUI;
import frontend.units.RiderUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collection;

public class GameController {
    Integer cellHeight;
    Integer cellWidth;
    Integer worldHeight;
    Integer worldWidth;

    Game game;
    //    /**
//     * @param cellHeight    The height of the cell in pixels
//     * @param cellWidth     The width of the cell in pixels
//     */
    public GameController(GraphicsContext graphicsContext, Game game) {
        this.game = game;
        this.worldHeight = game.getWorldHeight();
        this.worldWidth = game.getWorldWidth();
        cellWidth = (int) (graphicsContext.getCanvas().getWidth() / (worldWidth + 0.417d));
        cellHeight = (int) (graphicsContext.getCanvas().getHeight() / (worldHeight * 0.80));
        System.out.println(cellHeight + "," + cellWidth);
    }

    public Integer getCellHeight() {
        return cellHeight;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void selectCell(Game game, Location location) {
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

    public void updateGraphics(GraphicsContext graphicsContext) {
        //clear the canvas
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
        drawCells(graphicsContext);

    }

    public void drawCells(GraphicsContext graphicsContext) {
        for (Cell cell: game.getCells()){
            switch (cell.getTerrain().getTerrainType()){
                case WATER:
                    new WaterUI(gridLocationToDrawLocation(cell.getLocation()),cellHeight,cellWidth).drawMe(graphicsContext);
                case GRASS:
                    new GrassUI(gridLocationToDrawLocation(cell.getLocation()),cellHeight,cellWidth).drawMe(graphicsContext);
                case HILL:
                    new HillUI(gridLocationToDrawLocation(cell.getLocation()),cellHeight,cellWidth).drawMe(graphicsContext);
                case MOUNTAIN:
                    new MountainUI(gridLocationToDrawLocation(cell.getLocation()),cellHeight,cellWidth).drawMe(graphicsContext);
                case FOREST:
                    new ForestUI(gridLocationToDrawLocation(cell.getLocation()),cellHeight,cellWidth).drawMe(graphicsContext);

            }
        }

        for(Building building: game.getBuildings()){
            switch (building.getBuildingType()){
                case MINE:
                    new MineUI(gridLocationToDrawLocation(building.getLocation()),cellHeight,cellWidth,building.getOwner())
                            .drawMe(graphicsContext);
                case CASTLE:
                    new CastleUI(gridLocationToDrawLocation(building.getLocation()),cellHeight,cellWidth,building.getOwner())
                            .drawMe(graphicsContext);
            }
        }

        for(Unit unit: game.getUnits()){
            switch (unit.getUnitType()){
                case ARCHER:
                    new ArcherUI(gridLocationToDrawLocation(unit.getLocation()),cellHeight,cellWidth,unit.getOwner().getId());
                case RIDER:
                    new RiderUI(gridLocationToDrawLocation(unit.getLocation()),cellHeight,cellWidth,unit.getOwner().getId());
                case LANCER:
                    new LancerUI(gridLocationToDrawLocation(unit.getLocation()),cellHeight,cellWidth,unit.getOwner().getId());
            }
        }
    }

    private void drawBuilding(GraphicsContext graphicsContext, BuildingType buildingType, Location location, Integer ownerID) {
        Image image = getBuildingImage(buildingType);
        Image flag = getFlagImage(ownerID);
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

    private void drawUnit(GraphicsContext graphicsContext, UnitType unitType, Location location, Integer ownerID) {
        Image image = getUnitImage(unitType);
        Image ownerMarker = getOwnerMarker(ownerID);
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

    public Image getBuildingImage(BuildingType buildingType) {
        if (buildingType == null) throw new NullArgumentException("Null building type in image");

        switch (buildingType){
            case MINE:
                return new Image("file:mainModule/resources/mine.png", cellWidth, cellHeight, false, false);
            case CASTLE:
                return new Image("file:mainModule/resources/Castle.png", cellWidth, cellHeight, false, false);

        }
        throw new RuntimeException("No image for that building type: " + buildingType);
    }

    public Image getTerrainImage(Terrain terrain) {
        switch (terrain.getTerrainType()) {
            case GRASS:
                return new Image("file:mainModule/resources/cellGrass.png", cellWidth, cellHeight, false, false);

            case MOUNTAIN:
                return new Image("file:mainModule/resources/cellMountain.png", cellWidth, cellHeight, false, false);

            case FOREST:
                return new Image("file:mainModule/resources/cellForest.png", cellWidth, cellHeight, false, false);

            case WATER:
                return new Image("file:mainModule/resources/cellWater.png", cellWidth, cellHeight, false, false);

            case HILL:
                return new Image("file:mainModule/resources/cellHill.png", cellWidth, cellHeight, false, false);
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

    public Image getOwnerMarker(Integer ownerID) {
        //TODO retorno null asi no mas?
        if (ownerID == null) {
            return null;
        }
        if (ownerID == 1) {
            return new Image("file:mainModule/resources/blueMarker.png", cellWidth, cellHeight, false, false);
        } else if (ownerID == 2) {
            return new Image("file:mainModule/resources/redMarker.png", cellWidth, cellHeight, false, false);
        } else {
            throw new NoSuchPlayerException("The player " + ownerID + " does not exist.");
        }
    }

    public Image getFlagImage(Integer ownerID) {
        //TODO retorno null asi no mas? (es decir que no quiero imprimir ninguna imagen)
        if (ownerID == null) {
            return null;
        }
        if (ownerID==1) {
            return new Image("file:mainModule/resources/blueFlag.png", cellWidth, cellHeight, false, false);
        } else if (ownerID==2) {
            return new Image("file:mainModule/resources/redFlag.png", cellWidth, cellHeight, false, false);
        } else {
            throw new NoSuchPlayerException("The player " + ownerID + " does not exist.");
        }
    }



}
