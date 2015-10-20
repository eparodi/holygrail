//TODO PREGUNTAS A SANTIII SI ES CORRECTO
//TODO CAMBIAR DE NOMBRE, ES CONFLICTIVO
package backend.worldBuilding;


public class Location {
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
}

