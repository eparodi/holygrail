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
        Canvas canvas = new Canvas(1000,600);
        final Game game = new Game(15,10,"Jorge","Marcos");
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
                if (key.getCode().equals(KeyCode.L)){
                    game.attemptBuildUnit(UnitType.LANCER);
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

    public static void main(String[] args) {
        launch(args);
    }
}