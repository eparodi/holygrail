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

//        Canvas canvas = new Canvas(Screen.getPrimary().getBounds().getWidth()-200,Screen.getPrimary().getBounds().getHeight()-200);
        Canvas canvas = new Canvas(500,500);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene=new Scene(root, canvas.getWidth(), canvas.getHeight());
        root.getChildren().add(canvas);

        final Game game = new Game();
        final GameController gameController = new GameController(game.getWorldHeight(),game.getWorldWidth(),graphicsContext);
        canvas.autosize();
        root.autosize();

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
                if (key.getCode().equals(KeyCode.A))
                    game.attemptBuildUnit(UnitType.ARCHER);
                if (key.getCode().equals(KeyCode.L)){
                    game.attemptBuildUnit(UnitType.WARRIOR);
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