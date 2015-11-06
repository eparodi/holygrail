package backend.exceptions;

/**
 * TODO: Esta es la excepción de la que hablabamos y nos preguntamos si debería existir, ya que si le pasamos null arguments a un metodo es que nosotros estamos haciendo algo mal. Tiene 50 usos, y ningún try catch.
 */
public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String msg){
        super(msg);
    }
}
