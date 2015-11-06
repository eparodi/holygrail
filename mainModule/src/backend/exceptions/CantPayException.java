package backend.exceptions;

/**
 * Thrown when a Player does not have enough gold to pay.
 */
public class CantPayException extends RuntimeException {
    public CantPayException(String msg){
        super(msg);
    }
}
