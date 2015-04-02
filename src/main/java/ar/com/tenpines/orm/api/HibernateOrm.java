package ar.com.tenpines.orm.api;

import java.util.function.Function;

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
     * @param <T> The type of expected result
     * @return The result from the operation
     */
    <T> T doWithSession(Function<SessionContext, T> operation);

    /**
     * Executes an operation under a transaction, commiting the transaction if the operation finishes successfully.<br>
     *     rollbacking if the operation fails with an exception. A new session and transaction are created for to execute
     *     the operation.<br>
     * <br>
     *     This is a shorthand version for one-transaction-one-session operations
     * @param operation The operation to execute
     * @param <T> The type of expected result
     * @return The result of the operation
     */
    <T> T doInTransaction(Function<TransactionContext, T> operation);

    /**
     * Closes this ORM connections and frees all the resources
     */
    void close();
}
