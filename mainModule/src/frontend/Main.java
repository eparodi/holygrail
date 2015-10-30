package frontend;

import backend.Game;
import backend.units.UnitType;
import backend.worldBuilding.Cell;
import backend.worldBuilding.Location;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.event.TreeModelEvent;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        Pane root = new FlowPane();

        //TO CHANGE RESOLUTION OR WORLD HEIGHT MODIFY THIS:
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Canvas canvas = new Canvas(width-100,height-100);
        final Game game = new Game(14,10,"Jorge","Marcos");


        //END OF MODIFIABLE, DONT TOUCH THE REST

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene=new Scene(root, canvas.getWidth(), canvas.getHeight());


        //TODO ARREGLAR LA VENTANA Y PONER EVENTOS

        MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemExit = new MenuItem("Exit");
        mainMenu.getMenus().addAll(menuFile);
        menuFile.getItems().add(menuItemExit);
        root.getChildren().add(mainMenu);

        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        //TERMINA MENU


        root.getChildren().add(canvas);
        final GameController gameController = new GameController(graphicsContext,game);


        gameController.updateGraphics(graphicsContext);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gameController.attemptAction(game, e.getX(), e.getY());
                gameController.updateGraphics(graphicsContext);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
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
                gameController.updateGraphics(graphicsContext);
            }
        });

        saveLocation("asdasd",new Location(1000,2000));
        System.out.println("loadLocation = " + loadLocation("asdasd"));

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void saveLocation(String path, Location location){
        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(location);
            out.close();
            fileOut.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public static Location loadLocation(String path){
        Location location = null;
        try
        {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            location = (Location) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
        return location;
    }
}