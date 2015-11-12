package backend.exceptions;

/**
 * Thrown if the argument is null.
 */
public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String msg) {
        super(msg);
    }
}
