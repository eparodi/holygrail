package backend.exceptions;

/**
 * TODO: Esta es la excepci�n de la que hablabamos y nos preguntamos si deber�a existir, ya que si le pasamos null arguments a un metodo es que nosotros estamos haciendo algo mal. Tiene 50 usos, y ning�n try catch.
 */
public class NullArgumentException extends RuntimeException {
    public NullArgumentException(String msg){
        super(msg);
    }
}
