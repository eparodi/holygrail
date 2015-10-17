package backend.exceptions;

/**
 * Created by Julian Benitez on 10/16/2015.
 */
public class NoSuchUnitType extends RuntimeException {
    public NoSuchUnitType(String msg){
        super(msg);
    }
}
