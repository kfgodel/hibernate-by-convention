package ar.com.tenpines.orm.api.exceptions;

/**
 * This type represents an error on the ORM layer
 * Created by kfgodel on 03/04/15.
 */
public class OrmException extends RuntimeException {

    public OrmException(String message) {
        super(message);
    }

    public OrmException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrmException(Throwable cause) {
        super(cause);
    }
}
