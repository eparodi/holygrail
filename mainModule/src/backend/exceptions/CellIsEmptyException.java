package backend.exceptions;

public class CellIsEmptyException extends RuntimeException {
    public CellIsEmptyException(String msg){
        super(msg);
    }
}
