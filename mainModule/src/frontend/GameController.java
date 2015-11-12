package frontend;

import backend.Game;
import backend.building.Building;
import backend.exceptions.NullArgumentException;
import backend.units.Unit;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.io.*;

public class GameController {
    private Integer cellHeight;
    private Integer cellWidth;
    private Integer worldHeight;
    private Integer worldWidth;
    private Integer canvasHeight;
    private Integer canvasWidth;
    private Game game;

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
    }

    private void printLog(GraphicsContext graphicsContext) {
        String log = "";
        while (game.logHasNext()) {
            log += " " + game.getNextLog();
        }
        if (log != "") {
            graphicsContext.clearRect(0, graphicsContext.getCanvas().getHeight() - Main.getLogSize(),
                    graphicsContext.getCanvas().getWidth(), Main.getLogSize());
            Location printLogLocation = gridLocationToDrawLocation(new Location(0, worldHeight));
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText(log, printLogLocation.getX(), printLogLocation.getY() + Main.getLogSize());
        }
    }

    public void resetCellSize() {
        cellWidth = (int) (this.canvasWidth / (worldWidth + 0.417d));
        cellHeight = (int) (this.canvasHeight / (worldHeight * 0.80));
    }

    public void addCanvasSize(Double canvasHeight, Double canvasWidth) {
        this.canvasHeight = canvasHeight.intValue();
        this.canvasWidth = canvasWidth.intValue();
        resetCellSize();
    }

    public void attemptAction(double drawX, double drawY, GraphicsContext graphicsContext) {
        Location gridLocation = drawLocationToGridLocation((int) drawX, (int) drawY);
        if (gridLocation.getY() < worldHeight && gridLocation.getY() >= 0 &&
                gridLocation.getX() < worldWidth && gridLocation.getX() >= 0) {
            game.actionAttempt(gridLocation);
        }
        printLog(graphicsContext);
    }

    public void updateGraphics(GraphicsContext graphicsContext) {
        //clear the canvas
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(),
                graphicsContext.getCanvas().getHeight() - Main.getLogSize());
        drawCells(graphicsContext);

    }

    public void drawCells(GraphicsContext graphicsContext) {
        drawTerrain(graphicsContext);
        drawSelectedCell(graphicsContext);
        drawBuildings(graphicsContext);
        drawUnits(graphicsContext);

    }

    public void drawTerrain(GraphicsContext graphicsContext) {
        for (Cell cell : game.getCells()) {
            new TerrainUI(gridLocationToDrawLocation(cell.getLocation()), cell, cellHeight, cellWidth).drawMe(graphicsContext);
        }
    }

    public void drawBuildings(GraphicsContext graphicsContext) {
        Location drawLocation;
        for (Building building : game.getBuildings()) {
            drawLocation = gridLocationToDrawLocation(building.getLocation());
            new BuildingUI(drawLocation, building, cellHeight, cellWidth).drawMe(graphicsContext);
            printLog(graphicsContext);
        }
    }

    public void drawUnits(GraphicsContext graphicsContext) {
        Location drawLocation;
        for (Unit unit : game.getUnits()) {
            drawLocation = gridLocationToDrawLocation(unit.getLocation());
            new UnitUI(drawLocation, unit, cellHeight, cellWidth).drawMe(graphicsContext);
        }
    }

    public void drawSelectedCell(GraphicsContext graphicsContext) {
        new SelectedCellUI(gridLocationToDrawLocation(game.getSelectedLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
    }

    public Location gridLocationToDrawLocation(Location gridLocation) {
        Location drawLocation = new Location(0, 0);
        if (gridLocation == null) throw new NullArgumentException("null location");
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

    public Game loadNewGame(String path) {
        Game game = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Game not found or old version");
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
        }
        if (game != null) {
            this.game = game;
            initialize();
            resetCellSize();
            game.putHolyGrail(game);
        }
        return this.game;
    }

    public Game loadGame(String path) {
        Game game = null;
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (Game) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("Game not found or old version");
        } catch (ClassNotFoundException c) {
            System.out.println("Game class not found");
            c.printStackTrace();
        }
        if (game != null) {
            this.game = game;
            initialize();
            resetCellSize();
        }
        return this.game;
    }

    public void keyPressed(KeyEvent key, GraphicsContext graphicsContext) {
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

            if (game.endTurn()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Message");
                alert.setHeaderText("Congratulations");
                alert.setContentText("You have won!, do not try to move any more units please, start new game");

                alert.showAndWait();
            }
        }
        printLog(graphicsContext);
    }


}
