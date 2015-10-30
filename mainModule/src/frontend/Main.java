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
import javafx.scene.control.Button;
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

    public void start(Stage primaryStage) throws Exception {

        final Game game = new Game(16, 11, "Jorge", "Sergio");
        //END OF MODIFIABLE, DONT TOUCH THE REST


        //TODO ARREGLAR LA VENTANA Y PONER EVENTOS
        final GameController gameController = new GameController(game);

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(loadGameUI(gameController));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene loadGameUI(final GameController gameController) {
        Pane root = new FlowPane();

        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();

        Canvas canvas = new Canvas(width - 100, height - 100);
        gameController.addCanvasSize(canvas.getHeight(), canvas.getWidth());

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root, canvas.getWidth(), canvas.getHeight());

        final MenuBar mainMenu = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem menuItemExit = new MenuItem("Exit");
        MenuItem menuItemLoadGame = new MenuItem("Load game");
        MenuItem menuItemSaveGame = new MenuItem("Save game");

        menuItemExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menuItemSaveGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.saveGame("default");
            }
        });
        menuItemLoadGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("default");
                gameController.updateGraphics(graphicsContext);
            }
        });

        Menu menuStartNewGame = new Menu("New game");
        MenuItem menuItemGame1 = new MenuItem("Classic");
        menuItemGame1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("mainModule/src/saves/classic");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame2 = new MenuItem("Battle for the lake");
        menuItemGame2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("mainModule/src/saves/battle_for_the_lake");
                gameController.updateGraphics(graphicsContext);
            }
        });


        MenuItem menuItemGame3 = new MenuItem("Treasure Island");
        menuItemGame3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("mainModule/src/saves/treasure_island");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame4 = new MenuItem("Hidden Castle");
        menuItemGame4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("mainModule/src/saves/hidden_castle");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame5 = new MenuItem("King of the hill");
        menuItemGame5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadGame("mainModule/src/saves/king_of_the_hill");
                gameController.updateGraphics(graphicsContext);
            }
        });

        mainMenu.getMenus().addAll(menuFile, menuStartNewGame);
        menuStartNewGame.getItems().addAll(menuItemGame2, menuItemGame3, menuItemGame4, menuItemGame5);
        menuFile.getItems().addAll(menuItemExit, menuItemLoadGame, menuItemSaveGame);
        root.getChildren().add(mainMenu);

        root.getChildren().add(canvas);
        gameController.updateGraphics(graphicsContext);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gameController.attemptAction(e.getX(), e.getY() - mainMenu.getHeight());
                gameController.updateGraphics(graphicsContext);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                gameController.keyPressed(key);
                gameController.updateGraphics(graphicsContext);
            }
        });

        return scene;
    }

}