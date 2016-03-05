package ar.com.tenpines.orm.api.operations;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;

/**
 * This type represents an operation done under the context of a transaction.<br>
 * Commiting the transaction if the operation finishes successfully.<br>
 * Rollbacking if the operation fails with an exception. A new session and transaction are created for to execute
 * the operation.<br>
 * Created by kfgodel on 05/04/15.
 */
public interface TransactionOperation<R> extends SessionOperation<R> {

  /**
   * Executes this operation under the scope of a transaction  given as context.<br>
   *   It is assumed that the operation will never raise an exception to handle flow, instead
   *   it should use a polymorphic return value. If an exception is raised, it's considered
   *   an error and rollbacked
   *
   * @param transactionContext The transactional context to fetch objects from the database
   * @return The result of this operation
   */
  R applyWithTransactionOn(TransactionContext transactionContext);

  /**
   * Default implementation of a transaction operation, that allows usage as a session operation.<br>
   * It uses a transaction under the given sesion (it creates one if none exists)
   *
   * @param sessionContext The context from which to fetch persistent objects
   * @return The result of this operation
   */
  @Override
  default R applyWithSessionOn(SessionContext sessionContext) {
    return sessionContext.doUnderTransaction(this);
  }
}
