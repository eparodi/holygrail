package backend.worldBuilding;


import java.io.Serializable;

public class Location implements Serializable {
    private Integer x;
    private Integer y;


    public Location(Integer x,Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }


    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return getX().equals(location.getX()) && getY().equals(location.getY());

    }

    @Override
    public int hashCode() {
        return x.hashCode() * y.hashCode();
    }

    @Override
    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }

    /**
     * Calculates the distance between this location and another.
     *
     * @param location location.
     * @return Integer value with distance between locations.
     */
    public Integer distance(Location location) {
        Integer x1 = -this.getY();
        Integer x2 = -location.getY();
        Integer y1 = this.getY() % 2 == 0 ? this.getX() + this.getY() / 2 : this.getX() + (this.getY() + 1) / 2;
        Integer y2 = location.getY() % 2 == 0 ? location.getX() + location.getY() / 2 :
                location.getX() + (location.getY() + 1) / 2;
        Integer z1 = -x1 - y1;
        Integer z2 = -x2 - y2;

        Integer deltaX = Math.abs(x1 - x2);
        Integer deltaY = Math.abs(y1 - y2);
        Integer deltaZ = Math.abs(z1 - z2);

        return Math.max(Math.max(deltaX, deltaY), deltaZ);
    }
}

