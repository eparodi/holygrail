package backend.exceptions;

/**
 * Created by Julian Benitez on 10/17/2015.
 */
public class NullLocationException extends RuntimeException {
    public NullLocationException(String msg){
        super(msg);
    }
}
