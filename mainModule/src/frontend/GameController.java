package frontend;

import backend.Game;
import backend.Log;
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
import java.security.acl.LastOwnerException;

/**
 * Represents the Game Controller class.
 */
public class GameController {
    private Integer cellHeight;
    private Integer cellWidth;
    private Integer worldHeight;
    private Integer worldWidth;
    private Integer canvasHeight;
    private Integer canvasWidth;
    private Integer logHeight=7;
    private boolean hasGameFinished=false;

    private Game game;

    /**
     * Creates a GameController from a Game and executes initialize.
     *
     * @param game Current Game.
     */
    public GameController(Game game) {
        this.game = game;
        initialize();
    }

    /**
     * Loads the World Height and Width from the game.
     */
    private void initialize() {
        this.worldHeight = game.getWorldHeight();
        this.worldWidth = game.getWorldWidth();
    }

    /**
     * Prints the queued Log.
     * @param graphicsContext Graphic Context where the Log will be drawn.
     */
    private void printLog(GraphicsContext graphicsContext) {
        StringBuilder log = new StringBuilder();
        while (!Log.getInstance().isEmpty()   ) {
            log.append(" " + Log.getInstance().printLog() + " ");
        }
        if (log.length() != 0) {
            graphicsContext.clearRect(0, graphicsContext.getCanvas().getHeight() - cellHeight/2,
                    graphicsContext.getCanvas().getWidth(), cellHeight/2);
            Location printLogLocation = gridLocationToDrawLocation(new Location(0, worldHeight));
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillText(log.toString(), printLogLocation.getX(), printLogLocation.getY() + cellHeight/2);
        }
    }

    /**
     * Resets the Cells Size.
     */
    private void resetCellSize() {
        cellWidth = (int) (this.canvasWidth / (worldWidth + 0.417d));
        cellHeight = (int) ((this.canvasHeight-logHeight) / (worldHeight * 0.80));
    }

    /**
     * Sets the Canvas Size.
     * @param canvasHeight Canvas Height
     * @param canvasWidth Canvas Width.
     */
    public void addCanvasSize(Double canvasHeight, Double canvasWidth) {
        this.canvasHeight = canvasHeight.intValue();
        this.canvasWidth = canvasWidth.intValue();
        resetCellSize();
    }

    /**
     * Attempts to perform an action from a clicked location.
     * @param drawX X coordinate.
     * @param drawY Y coordinate.
     * @param graphicsContext Graphic Context of the Log.
     */
    public void attemptAction(double drawX, double drawY, GraphicsContext graphicsContext) {
        Location gridLocation = drawLocationToGridLocation((int) drawX, (int) drawY);
        if (gridLocation.getY() < worldHeight && gridLocation.getY() >= 0 &&
                gridLocation.getX() < worldWidth && gridLocation.getX() >= 0) {
            game.actionAttempt(gridLocation);
        }
        printLog(graphicsContext);
    }

    /**
     * Clears the Screen and updates the Game World Graphics.
     *
     * @param graphicsContext Graphic Context where the Cells are.
     */
    public void updateGraphics(GraphicsContext graphicsContext) {
        //clear the canvas
        graphicsContext.clearRect(0, 0, graphicsContext.getCanvas().getWidth(),
                graphicsContext.getCanvas().getHeight() - cellHeight/2);
        drawCells(graphicsContext);

    }

    /**
     * Draws the Cells on the Game World.
     *
     * @param graphicsContext Graphics Context where the Cells will be drawn.
     */
    private void drawCells(GraphicsContext graphicsContext) {
        drawTerrain(graphicsContext);
        drawSelectedCell(graphicsContext);
        drawBuildings(graphicsContext);
        drawUnits(graphicsContext);

    }

    /**
     * Draws the Terrain of every Cell.
     *
     * @param graphicsContext Graphics Context where the Terrain will be drawn.
     */
    private void drawTerrain(GraphicsContext graphicsContext) {
        for (Cell cell : game.getCells()) {
            new TerrainUI(gridLocationToDrawLocation(cell.getLocation()), cell, cellHeight, cellWidth).drawMe(graphicsContext);
        }
    }

    /**
     * Draws the Buildings in the Game.
     *
     * @param graphicsContext Graphics Context where the Buildings will be draw.
     */
    private void drawBuildings(GraphicsContext graphicsContext) {
        Location drawLocation;
        for (Building building : game.getBuildings()) {
            drawLocation = gridLocationToDrawLocation(building.getLocation());
            new BuildingUI(drawLocation, building, cellHeight, cellWidth).drawMe(graphicsContext);
            printLog(graphicsContext);
        }
    }

    /**
     * Draws the Units in the Game.
     *
     * @param graphicsContext Graphics Context where the Units will be drawn.
     */
    private void drawUnits(GraphicsContext graphicsContext) {
        Location drawLocation;
        for (Unit unit : game.getUnits()) {
            drawLocation = gridLocationToDrawLocation(unit.getLocation());
            new UnitUI(drawLocation, unit, cellHeight, cellWidth).drawMe(graphicsContext);
        }
    }

    /**
     * Draws the current Selected Cell.
     *
     * @param graphicsContext Graphics Context where the Selected Cell will be drawn.
     */
    private void drawSelectedCell(GraphicsContext graphicsContext) {
        new SelectedCellUI(gridLocationToDrawLocation(game.getSelectedLocation()), cellHeight, cellWidth).drawMe(graphicsContext);
    }

    /**
     * Returns a Pixel based Location transforming a Grid Cell Location.
     *
     * @param gridLocation Grid based Location on the Cell.
     * @return Pixel based Location.
     */
    private Location gridLocationToDrawLocation(Location gridLocation) {
        Location drawLocation = new Location(0, 0);
        if (gridLocation == null) {
            throw new NullArgumentException("null location");
        }
        drawLocation.setX(gridLocation.getY() % 2 == 0 ? gridLocation.getX() * cellWidth :
                gridLocation.getX() * cellWidth + cellWidth / 2); // Depende de fila par/impar
        drawLocation.setY(gridLocation.getY() * (cellHeight - cellHeight / 4));

        return drawLocation;
    }

    /**
     * Returns a Grid Cell Location transforming a pixel base Location.
     *
     * @param x X Coordinate.
     * @param y Y Coordinate.
     * @return Grid Cell Location.
     */
    private Location drawLocationToGridLocation(Integer x, Integer y) {
        Location gridLocation = new Location(0, 0);

        Double auxY = Math.floor(y / (((double) 3 / 4) * cellHeight));
        Double auxX = auxY.intValue() % 2 == 0 ? Math.floor(x / cellWidth) : Math.floor((x - cellWidth / 2) / cellWidth);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }

    /**
     * Saves the Game on the specified Path.
     * @param path Path to save the game at.
     */
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

    /**
     * Loads a New Game from a Custom Game MAP at the specified Path.
     *
     * @param path Path to load the Game maps from.
     * @return Game of a new loaded Map.
     */
    public Game loadNewGame(String path) {
        Game game = loadGame(path);
        game.putHolyGrail(game);
        return game;
    }

    /**
     * Loads the Game at the specified Path.
     *
     * @param path Path to load the game from.
     * @return Loaded Game.
     */
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
        hasGameFinished =false;
        return this.game;
    }

    /**
     * Performs the corresponding action depending on the pressed Key.
     *
     * @param key Pressed Key.
     * @param graphicsContext Graphic Context to draw if the game ends.
     */
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
                if(!hasGameFinished) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Message");
                    alert.setHeaderText("Congratulations");
                    alert.setContentText("You have won!, do not try to move any more units please, start new game");

                    alert.showAndWait();
                    hasGameFinished = true;
                }
            }
        }
        printLog(graphicsContext);
    }


}
