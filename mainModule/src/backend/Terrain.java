package backend;

import java.util.Objects;


public enum Terrain {
    GRASS, MOUNTAIN, FOREST, WATER
}
//
//public abstract class Terrain {
//    String name;
//    Integer movementCost;
//
//    public Terrain(String name, Integer movementCost){
//        this.name = name;
//        this.movementCost = movementCost;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Integer getMovementCost() {
//        return movementCost;
//    }
//
//    public int hashCode(){
//        return name.hashCode();
//    }
//
//    //TODO:make a decent equals
//    public boolean equals(Object obj){
//        return name.equals(obj.toString());
//    }
//
//    public String toString(){
//        return name;
//    }
//}
