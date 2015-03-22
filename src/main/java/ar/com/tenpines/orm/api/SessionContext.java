package ar.com.tenpines.orm.api;

import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type represents a context reification for an hibernate session.<br>
 *     Instances of this type are used to access session bound operations
 * Created by kfgodel on 22/03/15.
 */
public interface SessionContext {

    /**
     * Executes an operation under a transaction, commiting the transaction if the operation finishes successfully.<br>
     *     rollbacking if the operation fails with an exception
     * @param operation The operation to execute
     * @param <T> The type of expected result
     * @return The result of the operation
     */
    <T> T doInTransaction(Function<TransactionContext, T> operation);

    /**
     * @return The current session
     */
    Session getSession();

    /**
     * Closes the current session
     */
    void close();
}
