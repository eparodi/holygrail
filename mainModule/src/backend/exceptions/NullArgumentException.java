package backend.exceptions;

public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String msg){
        super(msg);
    }
}
