package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        //Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Group root = new Group();

        Canvas canvas = new Canvas(600,600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();



        drawCells(generateCellCollection(5, 6, 100, 100), graphicsContext);

        Unit unit = new Unit(3,4);
        unit.drawMe(graphicsContext);

        root.getChildren().add(canvas);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        graphicsContext.clearRect(e.getX() - 2, e.getY() - 2, 5, 5);

                        unit.moveTo(drawLocationToGridLocation(e.getX(),e.getY()));

                        drawCells(generateCellCollection(5, 6, 100, 100), graphicsContext);
                        unit.drawMe(graphicsContext);
                    }
                }
        );

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(new Scene(root   , canvas.getWidth(), canvas.getHeight()));
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

    public Collection<Cell> generateCellCollection(Integer collectionWidth, Integer collectionHeight, Integer cellHeight, Integer cellWidth){
        Collection<Cell> cellCollection = new ArrayList<>();
        Cell cell;

        for (int i=0 ; i < collectionWidth ; i++){
            for (int j=0 ; j < collectionHeight ; j++) {
                cell = new Cell(i,j);
                cellCollection.add(cell);
            }
        }
        return cellCollection;
    }

    public void drawCells(Collection<Cell> cellCollection, GraphicsContext graphicsContext){
        for (Cell cell: cellCollection){
            cell.drawMe(graphicsContext);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

