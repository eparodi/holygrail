package frontend;

import backend.building.Building;
import backend.exceptions.NullLocationException;
import backend.units.Unit;
import backend.units.UnitFactory;
import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.Terrain;
import backend.worldBuilding.World;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;



public class Main extends Application {
    int ancho=5;
    int alto=5;
    Player jugador1 = new Player("A");
    Player jugador2 = new Player("B");
    World mundo = new World(ancho,alto,jugador1,jugador2);

    Unit lancer = UnitFactory.buildUnit("archer", Terrain.GRASS, new Location(1, 2), jugador1);
    Unit enemy1 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 4), jugador2);
    Unit enemy2 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 2), jugador2);
    Unit enemy3 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 3), jugador2);
    Unit enemy4 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 1), jugador2);
    Unit enemy5 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 0), jugador2);
    FlowPane mainPanel = new FlowPane();
    final Canvas canvas = new Canvas(100*ancho+50,80*alto);
    final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();



    public void DrawInCell (String ImgFile, GraphicsContext gCtxt, Location drawLo){
        Image img = new Image(ImgFile, 100, 100, true, true);
        gCtxt.drawImage(img,
                gridLocationToCellLocation(drawLo).getX(),
                gridLocationToCellLocation(drawLo).getY()
        );
    }

    public Location gridLocationToCellLocation (Location gridLocation){
        int cellSize = 100;
        Location drawLocation = new Location(0, 0);

        drawLocation.setX(gridLocation.getY() % 2 == 0 ? gridLocation.getX() * cellSize : gridLocation.getX() * cellSize + cellSize / 2);
        drawLocation.setY(gridLocation.getY() * (cellSize - cellSize / 4));

        return drawLocation;
    }


    public void redrawMap() {
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                DrawInCell("file:C:\\cellGreen.png", graphicsContext, new Location(i, j));
                Building edificio = mundo.getCellAt(new Location(i, j)).getBuilding();
                if (edificio != null) {
                    DrawInCell("file:C:\\castillo.png", graphicsContext, new Location(i, j));
                }
                Unit unidadencell = mundo.getCellAt(new Location(i, j)).getUnit();
                if (unidadencell != null) {
                    if (unidadencell.getOwner() == jugador2)
                        DrawInCell("file:C:\\may.png", graphicsContext, new Location(i, j));
                    else
                        DrawInCell("file:C:\\ash.png", graphicsContext, new Location(i, j));
                }
            }
        }
    }

    public Location cellLocationToGridLocation (Double x, Double y){
        Location gridLocation = new Location(0, 0);
        int auxY,auxX;
        if(y%80<70 && y%80>10) {
            auxY = (int) Math.floor(y / 75);
            auxX = auxY % 2 == 0 ? (int) Math.floor(x / 100) : (int) Math.floor((x - 50) / 100);
        }else{
            auxX=-1;
            auxY=-1;
        }
        return new Location(auxX,auxY);
    }

    public static void main (String[]args) {
        launch(args);
    }

    public void triggerMapClick(Location clickedCell,World mundo,Player jug1,Player jug2,Unit selUnit){
        if(clickedCell.getX()>=0 && clickedCell.getX()<ancho && clickedCell.getY()>=0 && clickedCell.getY()<alto){
            if(selUnit.getRange()>=mundo.distance(selUnit.getLocation(),clickedCell)) {
                if ((mundo.getCellAt(clickedCell).getUnit() == null)) {
                    mundo.moveUnit(selUnit.getLocation(), clickedCell);
                } else {
                    if (mundo.getCellAt(clickedCell).getUnit().getOwner() == jug2) {
                        mundo.skirmish(selUnit, mundo.getCellAt(clickedCell).getUnit());
                    }
                }
            }
            redrawMap();

        }
    }

    public void start(Stage primaryStage) throws Exception{
        mundo.addUnit(lancer);
        mundo.addUnit(enemy1);
        mundo.addUnit(enemy2);
        mundo.addUnit(enemy3);
        mundo.addUnit(enemy4);
        mundo.addUnit(enemy5);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Location lugar = cellLocationToGridLocation(e.getX(), e.getY());
                        triggerMapClick(lugar, mundo, jugador1, jugador2, lancer);
                    }
                }
        );
        mainPanel.getChildren().add(canvas);
        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(new Scene(mainPanel, canvas.getWidth(), canvas.getHeight()));
        primaryStage.show();
        redrawMap();
    }
}