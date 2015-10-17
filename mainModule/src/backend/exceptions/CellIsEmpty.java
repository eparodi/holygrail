package backend.exceptions;

public class CellIsEmpty extends RuntimeException {
    public CellIsEmpty(String msg){
        super(msg);
    }
}
