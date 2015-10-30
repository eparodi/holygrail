package frontend;

import backend.Game;
import backend.building.Building;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;

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
        drawTerrain(graphicsContext);
        drawSelectedCell(graphicsContext);
        drawBuidings(graphicsContext);
        drawUnits(graphicsContext);

    }

    public void drawTerrain(GraphicsContext graphicsContext) {
        for (Cell cell : game.getCells()) {
            new TerrainUI(gridLocationToDrawLocation(cell.getLocation()), cell, cellHeight, cellWidth).drawMe(graphicsContext);
//            switch (cell.getTerrain().getTerrainType()) {
//                case WATER:
//                    new WaterUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
//                    break;
//                case GRASS:
//                    new GrassUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
//                    break;
//                case HILL:
//                    new HillUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
//                    break;
//                case MOUNTAIN:
//                    new MountainUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
//                    break;
//                case FOREST:
//                    new ForestUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
//                    break;
//
//
//            }
        }
    }

    public void drawBuidings(GraphicsContext graphicsContext) {
        for (Building building : game.getBuildings()) {
            new BuildingUI(gridLocationToDrawLocation(building.getLocation()), building, cellHeight, cellWidth).drawMe(graphicsContext);

        }
    }

    public void drawUnits(GraphicsContext graphicsContext) {
        for (Unit unit : game.getUnits()) {
            new UnitUI(gridLocationToDrawLocation(unit.getLocation()), unit, cellHeight, cellWidth).drawMe(graphicsContext);

        }
    }

    public void drawSelectedCell(GraphicsContext graphicsContext){
        new SelectedCellUI(gridLocationToDrawLocation(game.getSelectedLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
    }

    public Location gridLocationToDrawLocation(Location gridLocation) {
        Location drawLocation = new Location(0, 0);

        drawLocation.setX(gridLocation.getY() % 2 == 0 ? gridLocation.getX() * cellWidth :
                gridLocation.getX() * cellWidth + cellWidth / 2); // Depende de fila par/impar
        drawLocation.setY(gridLocation.getY() * (cellHeight - cellHeight / 4));

        return drawLocation;
    }

    public Location drawLocationToGridLocation(Integer x, Integer y) {
        Location gridLocation = new Location(0, 0);

        Double auxY = Math.floor(y / (((double) 3 / 4) * cellHeight));
        Double auxX = auxY.intValue() % 2 == 0 ? Math.floor(x / cellWidth) : Math.floor((x - cellWidth / 2) / cellWidth);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }


}
