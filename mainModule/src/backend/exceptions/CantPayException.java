package backend.exceptions;

public class CantPayException extends RuntimeException {
    public CantPayException(String msg){
        super(msg);
    }
}
