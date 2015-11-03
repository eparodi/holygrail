package backend.terrain;

import java.io.Serializable;

public class Mountain extends Terrain implements Serializable {
    public Mountain() {
        super(1.2, 1.5, 0.7, 2, 5);//Slow speed and high endurance cost
    }
}
