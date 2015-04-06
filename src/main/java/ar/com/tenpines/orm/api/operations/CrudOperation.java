package ar.com.tenpines.orm.api.operations;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import org.hibernate.Session;

/**
 * This type represents an operation that models one of the possible actions over persistent entities.<br>
 *     Usually multiple find operation, are found for a single type
 *
 * Created by kfgodel on 05/04/15.
 */
public interface CrudOperation<R> extends SessionOperation<Nary<R>>, TransactionOperation<Nary<R>> {

    /**
     * Executes this operation using the given session to access persistent object
     * @param currentSession The active session to fetch or update persistent objects
     * @return The operation result as a set of possible objects (0, 1, or N)
     */
    Nary<R> applyUsing(Session currentSession);


    /**
     * Default implementation that allows this crud operation to be used as a transaction operation.
     *
     * @param transactionContext The transactional context to fetch objects from the database
     * @return The result of this operation
     */
    @Override
    default Nary<R> applyUnder(TransactionContext transactionContext){
        return transactionContext.perform(this);
    }

    /**
     * Default implementation that allows this crud operation to be used as a session operation.
     * @param sessionContext The context from which to fetch persistent objects
     * @return The result of this operation
     */
    @Override
    default Nary<R> applyWith(SessionContext sessionContext){
        return sessionContext.perform(this);
    };
}
