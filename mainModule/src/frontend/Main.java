package frontend;

import backend.worldBuilding.Location;
import backend.worldBuilding.Player;
import backend.worldBuilding.World;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;


public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Canvas canvas = new Canvas(600,600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Player jugador1 = new Player("A");
        Player jugador2 = new Player("B");
        World mundo = new World(5,5,jugador1,jugador2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Image terrain = new Image("file:C:\\cellGreen.png", 100, 100, true, true );
                graphicsContext.drawImage(terrain,
                        gridLocationToDrawLocation(new Location(i, j)).getX(),
                        gridLocationToDrawLocation(new Location(i, j)).getY()
                );

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