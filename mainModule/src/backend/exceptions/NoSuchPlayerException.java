package backend.exceptions;

/**
 * Thrown if an non existing Player is called.
 */
public class NoSuchPlayerException extends RuntimeException {
    public NoSuchPlayerException(String msg) {
        super(msg);
    }
}
