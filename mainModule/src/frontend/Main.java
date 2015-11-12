package frontend;

import backend.Game;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private static Integer LOG_SIZE = 30;

    public void start(Stage primaryStage) throws Exception {

        final Game game = new Game(16, 11);
        //END OF MODIFIABLE, DONT TOUCH THE REST


        final GameController gameController = new GameController(game);

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(loadGameUI(gameController));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Integer getLogSize() {
        return LOG_SIZE;
    }

    public static Scene loadGameUI(final GameController gameController) {
        Pane root = new FlowPane();

        double width = Screen.getPrimary().getVisualBounds().getWidth();
        double height = Screen.getPrimary().getVisualBounds().getHeight();

        Canvas canvas = new Canvas(width - 100, height - 100);
        gameController.addCanvasSize(canvas.getHeight(), canvas.getWidth());

        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Scene scene = new Scene(root, canvas.getWidth(), canvas.getHeight() + LOG_SIZE);

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
                gameController.loadNewGame("mainModule/src/saves/classic");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame2 = new MenuItem("Battle for the lake");
        menuItemGame2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadNewGame("mainModule/src/saves/battle_for_the_lake");
                gameController.updateGraphics(graphicsContext);
            }
        });


        MenuItem menuItemGame3 = new MenuItem("Treasure Island");
        menuItemGame3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadNewGame("mainModule/src/saves/treasure_island");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame4 = new MenuItem("Hidden Castle");
        menuItemGame4.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadNewGame("mainModule/src/saves/hidden_castle");
                gameController.updateGraphics(graphicsContext);
            }
        });

        MenuItem menuItemGame5 = new MenuItem("King of the hill");
        menuItemGame5.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameController.loadNewGame("mainModule/src/saves/king_of_the_hill");
                gameController.updateGraphics(graphicsContext);
            }
        });

        Menu menuHelp = new Menu("Help");
        MenuItem menuItemRules = new MenuItem("Rules");
        menuItemRules.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                Alert rules = new Alert(Alert.AlertType.INFORMATION);
                rules.setTitle("Game Rules");
                rules.setHeaderText("GAME RULES");
                rules.setContentText("First Objective: Dig around the map, find the Holy Grail, and bring it back to your Castle.\n" +
                        "Secondary Objective: Conquer enemy's Castle.\n\n" +
                        "CONTROLS:\n" +
                        "A to build an Archer\n" +
                        "R to build a Rider\n" +
                        "L to build a Lancer\n" +
                        "D to dig with a unit selected\n" +
                        "Space Bar to change player turn\n" +
                        "Left Click to select a cell and interact with units.\n\n" +
                        "IMPORTANT:\n" +
                        "Green bar over units represent their actual life.\n" +
                        "Blue bar over units represent their action points(AP). AP are used to move, attack and dig." +
                        "AP is restored after each turn.\n\n");

                rules.showAndWait();
            }
        });


        mainMenu.getMenus().addAll(menuFile, menuStartNewGame, menuHelp);
        menuFile.getItems().addAll(menuItemExit, menuItemLoadGame, menuItemSaveGame);
        menuStartNewGame.getItems().addAll(menuItemGame2, menuItemGame3, menuItemGame4, menuItemGame5);
        menuHelp.getItems().addAll(menuItemRules);
        root.getChildren().add(mainMenu);

        Label label1 = new Label("Name:");
        TextField textField = new TextField();
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);

        root.getChildren().add(canvas);
        gameController.updateGraphics(graphicsContext);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                gameController.attemptAction(e.getX(), e.getY() - mainMenu.getHeight(), graphicsContext);
                gameController.updateGraphics(graphicsContext);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                gameController.keyPressed(key, graphicsContext);
                gameController.updateGraphics(graphicsContext);
            }
        });

        return scene;
    }

}