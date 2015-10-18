package frontend;

import backend.building.Building;
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
import java.util.ArrayList;
import java.util.Collection;


public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        final Canvas canvas = new Canvas(600,600);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();


        final Player jugador1 = new Player("A");
        final Player jugador2 = new Player("B");
        final World mundo = new World(5,5,jugador1,jugador2);

        final Unit lancer = UnitFactory.buildUnit("archer", Terrain.GRASS, new Location(1, 1), jugador1);
        Unit enemy1 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 4), jugador2);
        Unit enemy2 = UnitFactory.buildUnit("lancer", Terrain.GRASS, new Location(3, 2), jugador2);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Location lugar=drawLocationToGridLocation(e.getX(),e.getY());
                        if(lancer.getRange()>=mundo.distance(lancer.getLocation(),lugar)) {
                            if ((mundo.getCellAt(lugar).getUnit() == null)) {
                                mundo.moveUnit(lancer.getLocation(), lugar);
                            } else {
                                if (mundo.getCellAt(lugar).getUnit().getOwner() == jugador2) {
                                    mundo.skirmish(lancer, mundo.getCellAt(lugar).getUnit());
                                }
                            }
                        }



                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                Image terrain = new Image("file:C:\\cellGreen.png", 100, 100, true, true);
                                graphicsContext.drawImage(terrain,
                                        gridLocationToDrawLocation(new Location(i, j)).getX(),
                                        gridLocationToDrawLocation(new Location(i, j)).getY()
                                );
                                Unit unidadencell = mundo.getCellAt(new Location(i, j)).getUnit();
                                if (unidadencell != null) {
                                    Image unidad = new Image("file:C:\\archer.png", 100, 100, true, true);
                                    if (unidadencell.getOwner() == jugador2)
                                        unidad = new Image("file:C:\\may.png", 100, 100, true, true);
                                    graphicsContext.drawImage(unidad,
                                            gridLocationToDrawLocation(new Location(i, j)).getX(),
                                            gridLocationToDrawLocation(new Location(i, j)).getY()
                                    );
                                }
                                Building edificio = mundo.getCellAt(new Location(i, j)).getBuilding();
                                if (edificio != null) {
                                    Image castillo = new Image("file:C:\\castillo.png", 100, 100, true, true);
                                    graphicsContext.drawImage(castillo,
                                            gridLocationToDrawLocation(new Location(i, j)).getX(),
                                            gridLocationToDrawLocation(new Location(i, j)).getY()
                                    );
                                }

                            }
                        }

                    }
                }
        );

        mundo.addUnit(lancer);
        mundo.addUnit(enemy1);
        mundo.addUnit(enemy2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Image terrain = new Image("file:C:\\cellGreen.png", 100, 100, true, true);
                graphicsContext.drawImage(terrain,
                        gridLocationToDrawLocation(new Location(i, j)).getX(),
                        gridLocationToDrawLocation(new Location(i, j)).getY()
                );
                Unit unidadencell = mundo.getCellAt(new Location(i, j)).getUnit();
                if (unidadencell != null) {
                    Image unidad = new Image("file:C:\\ash.png", 100, 100, true, true);
                    if (unidadencell.getOwner() == jugador2)
                        unidad = new Image("file:C:\\may.png", 100, 100, true, true);
                    graphicsContext.drawImage(unidad,
                            gridLocationToDrawLocation(new Location(i, j)).getX(),
                            gridLocationToDrawLocation(new Location(i, j)).getY()
                    );
                }
                Building edificio = mundo.getCellAt(new Location(i, j)).getBuilding();
                if (edificio != null) {
                    Image castillo = new Image("file:C:\\castillo.png", 100, 100, true, true);
                    graphicsContext.drawImage(castillo,
                            gridLocationToDrawLocation(new Location(i, j)).getX(),
                            gridLocationToDrawLocation(new Location(i, j)).getY()
                    );
                }

            }
        }




        root.getChildren().add(canvas);
        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(new Scene(root, canvas.getWidth(), canvas.getHeight()));
        primaryStage.show();
    }


    public Location gridLocationToDrawLocation(Location gridLocation){
        int cellSize=100;
        Location drawLocation = new Location(0,0);

        drawLocation.setX(gridLocation.getY() %2 == 0? gridLocation.getX()*cellSize:gridLocation.getX()*cellSize + cellSize/2);
        drawLocation.setY(gridLocation.getY() *(cellSize - cellSize/4));

        return drawLocation;
    }

    public Location drawLocationToGridLocation(Double x,Double y){
        Location gridLocation = new Location(0,0);

        Double auxY = Math.floor(y/75);
        Double auxX = auxY.intValue() % 2 == 0? Math.floor(x/100):Math.floor((x-50)/100);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }



    public static void main(String[] args) {
        launch(args);
    }
}