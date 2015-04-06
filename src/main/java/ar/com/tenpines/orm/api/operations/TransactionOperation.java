package ar.com.tenpines.orm.api.operations;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;

/**
 * This type represents an operation done under the context of a transaction
 * Created by kfgodel on 05/04/15.
 */
public interface TransactionOperation<R> extends SessionOperation<R> {

    /**
     * Executes this operation under the scope of a transaction  goven as context
     * @param transactionContext The transactional context to fetch objects from the database
     * @return The result of this operation
     */
    R applyUnder(TransactionContext transactionContext);

    /**
     * Default implementation of a transaction operation, that allows usage as a session operation.<br>
     *     It uses a transaction under the given sesion (it creates one if none exists)
     * @param sessionContext The context from which to fetch persistent objects
     * @return The result of this operation
     */
    @Override
    default R applyWith(SessionContext sessionContext){
        return sessionContext.doUnderTransaction(this);
    }
}
