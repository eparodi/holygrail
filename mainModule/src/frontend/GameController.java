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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
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
    public GameController(Game game) {
        this.game = game;
        initialize();
    }

    private void initialize() {
        this.worldHeight = game.getWorldHeight();
        this.worldWidth = game.getWorldWidth();
        System.out.println(cellHeight + "," + cellWidth);
    }

    public void addCanvasSize(double canvasHeight, double canvasWidth) {
        cellWidth = (int) (canvasWidth / (worldWidth + 0.417d));
        cellHeight = (int) (canvasHeight / (worldHeight * 0.80));
    }

    public Integer getCellHeight() {
        return cellHeight;
    }

    public Integer getCellWidth() {
        return cellWidth;
    }

    public void attemptAction(double drawX, double drawY) {
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
            switch (cell.getTerrain().getTerrainType()) {
                case WATER:
                    new WaterUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
                    break;
                case GRASS:
                    new GrassUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
                    break;
                case HILL:
                    new HillUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
                    break;
                case MOUNTAIN:
                    new MountainUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
                    break;
                case FOREST:
                    new ForestUI(gridLocationToDrawLocation(cell.getLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
                    break;


            }
        }
    }

    public void drawBuidings(GraphicsContext graphicsContext) {
        for (Building building : game.getBuildings()) {
            switch (building.getBuildingType()) {
                case MINE:
                    new MineUI(gridLocationToDrawLocation(building.getLocation()), cellHeight, cellWidth, building.getOwner())
                            .drawMe(graphicsContext);
                    break;
                case CASTLE:
                    new CastleUI(gridLocationToDrawLocation(building.getLocation()), cellHeight, cellWidth, building.getOwner())
                            .drawMe(graphicsContext);
                    break;
            }
        }
    }

    public void drawUnits(GraphicsContext graphicsContext) {
        for (Unit unit : game.getUnits()) {
            switch (unit.getUnitType()) {
                case ARCHER:
                    new ArcherUI(gridLocationToDrawLocation(unit.getLocation()), cellHeight, cellWidth,
                            unit.getOwner().getId(), (double) unit.getHealth() / unit.getMaxHealth()).drawMe(graphicsContext);
                    break;
                case RIDER:
                    new RiderUI(gridLocationToDrawLocation(unit.getLocation()), cellHeight, cellWidth,
                            unit.getOwner().getId(), (double) unit.getHealth() / unit.getMaxHealth()).drawMe(graphicsContext);
                    break;
                case LANCER:
                    new LancerUI(gridLocationToDrawLocation(unit.getLocation()), cellHeight, cellWidth,
                            unit.getOwner().getId(), (double) unit.getHealth() / unit.getMaxHealth()).drawMe(graphicsContext);
                    break;
            }
        }
    }

    public void drawSelectedCell(GraphicsContext graphicsContext) {
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

    public void saveGame(String path) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Game loadGame(String path){
        Game game = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("game class not found");
            c.printStackTrace();
        }
        this.game = game;
        initialize();
        return game;
    }

    public void keyPressed(KeyEvent key) {
        if (key.getCode().equals(KeyCode.A)) {
            game.attemptBuildArcher();
        }
        if (key.getCode().equals(KeyCode.L)) {
            game.attemptBuildLancer();
        }
        if (key.getCode().equals(KeyCode.R)) {
            game.attemptBuildRider();
        }
        if (key.getCode().equals(KeyCode.D)) {
            game.pickItemAttempt();
        }

        if (key.getCode().equals(KeyCode.SPACE)) {
            game.endTurn();
        }
    }


}
