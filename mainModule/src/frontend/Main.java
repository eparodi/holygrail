package frontend;

import backend.Game;
import backend.units.UnitType;
import backend.worldBuilding.Location;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();

        //TO CHANGE RESOLUTION OR WORLD HEIGHT MODIFY THIS:
        Canvas canvas = new Canvas(1000,700);
        final Game game = new Game(10,15,"Jorge","Marcos");
        //END OF MODIFIABLE, DONT TOUCH THE REST

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene=new Scene(root, canvas.getWidth(), canvas.getHeight());
        root.getChildren().add(canvas);

        final GameController gameController = new GameController(game.getWorldHeight(),game.getWorldWidth(),graphicsContext);

        gameController.updateGraphics(graphicsContext, game.getCellUIData());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gameController.attemptAction(game,e.getX(),e.getY());
                gameController.updateGraphics(graphicsContext, game.getCellUIData());
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.A)){
                    game.attemptBuildUnit(UnitType.ARCHER);
                }
                if (key.getCode().equals(KeyCode.W)){
                    game.attemptBuildUnit(UnitType.WARRIOR);
                }
                if (key.getCode().equals(KeyCode.R)){
                    game.attemptBuildUnit(UnitType.RIDER);
                }

                if (key.getCode().equals(KeyCode.SPACE))
                    game.endTurn();
                gameController.updateGraphics(graphicsContext, game.getCellUIData());
            }
        });

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public Location drawLocationToGridLocation(Double x,Double y, Integer cellHeight, Integer cellWidth){
        Location gridLocation = new Location(0,0);

        //a new row of hexagonal cells start at 75% of cell's height
        Double auxY = Math.floor(y/(cellHeight*.75));
        //odd rows are moved cellWidth/2 to the right
        Double auxX = auxY.intValue() % 2 == 0? Math.floor(x/cellWidth):Math.floor((x-cellWidth/2)/cellWidth);

        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }

    public static void main(String[] args) {
        launch(args);
    }
}