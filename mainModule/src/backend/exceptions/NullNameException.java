package backend.exceptions;


public class NullNameException extends RuntimeException{
    public NullNameException(String msg){
        super(msg);
    }
}
