package backend.terrain;

import java.io.Serializable;

/**
 * Represents Forest Terrain of a Cell.
 */
public class Forest extends Terrain implements Serializable {
    public Forest() {
        super(1.5, 0.8, 0.96, 3, 2);
    }
}
