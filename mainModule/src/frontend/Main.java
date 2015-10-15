package frontend;

import backend.Attack;
import backend.units.Archer;
import backend.terrains.Mountain;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Created by Julian Benitez on 10/12/2015.
 */
public class Main extends Application{

    static public int SCREEN_HEIGHT = 600;
    static public int SCREEN_WIDTH = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Removed the graphics

        Group root = new Group();

        Canvas canvas = new Canvas(SCREEN_WIDTH,SCREEN_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Archer archer = new Archer(1);
        Archer target = new Archer(1);

        Attack attack = archer.getAttack(new Mountain());
        target.recieveDamage(attack,new Mountain());

        root.getChildren().add(canvas);

        primaryStage.setTitle("El Santo Grial");
        primaryStage.setScene(new Scene(root   , SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }

   /* public Location drawLocationToGridLocation(Double x,Double y){
        Location gridLocation = new Location(0,0);

        Double auxY = Math.floor(y/75);
        Double auxX = auxY.intValue() % 2 == 0? Math.floor(x/100):Math.floor((x-50)/100);



        gridLocation.setY(auxY.intValue());
        gridLocation.setX(auxX.intValue());

        return gridLocation;
    }

    public void paintEverything(GraphicsContext graphicsContext){

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
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
