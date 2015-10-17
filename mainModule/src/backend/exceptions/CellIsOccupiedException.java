package backend.exceptions;

/**
 * Created by Julian Benitez on 10/17/2015.
 */
public class CellIsOccupiedException extends RuntimeException {
    public CellIsOccupiedException(String msg){
        super(msg);
    }
}
