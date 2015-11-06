package backend.terrain;

import java.io.Serializable;

/**
 * Represents Hills Terrain of a Cell.
 */
public class Hill extends Terrain implements Serializable {
    public Hill(){
        super(1.2,1.4,1.2,4,3);
    }
}
