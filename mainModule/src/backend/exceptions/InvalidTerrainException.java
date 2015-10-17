package backend.exceptions;

public class InvalidTerrainException extends RuntimeException {
    public InvalidTerrainException(String msg){
        super(msg);
    }
}
