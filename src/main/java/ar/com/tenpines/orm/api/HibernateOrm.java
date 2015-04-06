package ar.com.tenpines.orm.api;

import ar.com.tenpines.orm.api.operations.SessionOperation;
import ar.com.tenpines.orm.api.operations.TransactionOperation;

/**
 * This type represents hibernate as an ORM layer over the database.<br>
 *     It hides unused hibernate features and exposes the ones we use
 *
 * Created by kfgodel on 22/03/15.
 */
public interface HibernateOrm {

    /**
     * Executes an operation that needs a hibernate session to be resolved.<br>
     *     Returns any result the operation generates
     * @param operation The operation to do with a session
     * @param <R> The type of expected result
     * @return The result from the operation
     */
    <R> R doWithSession(SessionOperation<R> operation);

    /**
     * Executes an operation under a transaction, commiting the transaction if the operation finishes successfully.<br>
     *     rollbacking if the operation fails with an exception. A new session and transaction are created for to execute
     *     the operation.<br>
     * <br>
     *     This is a shorthand version for one-transaction-one-session operations
     * @param operation The operation to execute
     * @param <R> The type of expected result
     * @return The result of the operation
     */
    <R> R doUnderTransaction(TransactionOperation<R> operation);

    /**
     * Closes this ORM connections and frees all the resources
     */
    void close();
}
