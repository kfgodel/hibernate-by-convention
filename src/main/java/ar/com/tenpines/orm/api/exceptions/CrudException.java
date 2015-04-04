package ar.com.tenpines.orm.api.exceptions;

/**
 * This type represents an error con CRUD operations
 * Created by kfgodel on 03/04/15.
 */
public class CrudException extends OrmException {

    public CrudException(String message) {
        super(message);
    }

    public CrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrudException(Throwable cause) {
        super(cause);
    }
}
