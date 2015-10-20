package backend.exceptions;

/**
 * Created by Julian Benitez on 10/16/2015.
 */
public class NoSuchUnitTypeException extends RuntimeException {
    public NoSuchUnitTypeException(String msg){
        super(msg);
    }
}
