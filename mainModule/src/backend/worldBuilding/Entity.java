package backend.worldBuilding;

import backend.worldBuilding.Location;

public abstract class Entity {
    private Location gridLocation;

    public Entity(){
        gridLocation = new Location(0,0);
    }

    public Location getGridLocation(){
        return gridLocation;
    }

    //No deberia esta aca
    public Integer distanceToLocation(Location target){
        //this assumes that both target and origin represent hexagons in a hex matrix

        Integer deltaX = Math.abs( target.getX() - gridLocation.getX());
        Integer deltaY = Math.abs( target.getY() - gridLocation.getY());
        Integer deltaZ = Math.abs( -target.getX()-target.getY() +gridLocation.getX()+gridLocation.getY());

        return Math.max(deltaX,Math.max(deltaY,deltaZ));
    }
}
