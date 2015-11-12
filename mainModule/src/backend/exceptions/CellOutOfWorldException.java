package backend.exceptions;

/**
 * Thrown if a Cell outside the World boundaries is trying to be accessed.
 */
public class CellOutOfWorldException extends RuntimeException {
    public CellOutOfWorldException(String msg) {
        super(msg);
    }
}
