package frontend;

import backend.Game;
import backend.worldBuilding.Location;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;


public class Main extends Application {
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Canvas canvas = new Canvas(1000,600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene=new Scene(root, canvas.getWidth(), canvas.getHeight());
        root.getChildren().add(canvas);

        Game game = new Game();
        GameController gameController = new GameController(100,100);
        canvas.autosize();
        root.autosize();

        //graphicsContext.fillRect(100,100,500,500);
        game.attemptBuildUnit("Archer");
        //este comando mueve la unidad :D
        //game.actionAttempt(new Location(2,3));
        gameController.updateGraphics(graphicsContext, game.getCellUIData());

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