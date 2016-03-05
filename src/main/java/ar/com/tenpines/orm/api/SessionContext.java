package ar.com.tenpines.orm.api;

import ar.com.tenpines.orm.api.crud.CrudProvider;
import ar.com.tenpines.orm.api.operations.TransactionOperation;
import ar.com.tenpines.orm.impl.contexts.Closeable;
import org.hibernate.Session;


/**
 * This type represents a context reification for an hibernate session.<br>
 *     Instances of this type are used to access session bound operations. It also offers basic crud operations
 * Created by kfgodel on 22/03/15.
 */
public interface SessionContext extends CrudProvider, Closeable{

    /**
     * Executes an operation under a transaction, committing the transaction if the operation finishes successfully.<br>
     *     rollbacking if the operation fails with an un-controlled exception
     * @param operation The operation to execute
     * @param <R> The type of expected result
     * @return The result of the operation
     */
    <R> R doUnderTransaction(TransactionOperation<R> operation);

    /**
     * @return The current session
     */
    Session getSession();

    /**
     * Closes the current session
     */
    void close();
}
