package backend.exceptions;

public class CellOutOfWorldException extends RuntimeException{
    public CellOutOfWorldException(String msg){
        super(msg);
    }
}
