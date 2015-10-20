package backend.exceptions;

/**
 * Created by Julian Benitez on 10/16/2015.
 */
public class NoSuchLifeImageException extends RuntimeException {
    public NoSuchLifeImageException(String msg){
        super(msg);
    }
}
