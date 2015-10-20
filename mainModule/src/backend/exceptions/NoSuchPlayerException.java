package backend.exceptions;

public class NoSuchPlayerException extends RuntimeException {
    public NoSuchPlayerException(String msg){
        super(msg);
    }
}
