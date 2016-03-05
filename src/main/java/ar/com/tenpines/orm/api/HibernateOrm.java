package ar.com.tenpines.orm.api;

import ar.com.tenpines.orm.api.operations.SessionOperation;
import ar.com.tenpines.orm.api.operations.TransactionOperation;

/**
 * This type represents hibernate as an ORM layer over the database.<br>
 * This type simplifies hibernate usage by hiding unused features and exposing the ones we do
 * <p/>
 * Created by kfgodel on 22/03/15.
 */
public interface HibernateOrm {

  /**
   * Executes an operation that needs a hibernate session to be resolved.<br>
   * Returns any result the operation generates.<br>
   * A session is created within a context, if there's no previous one, the existing session
   * is used if there's one in the current thread context
   *
   * @param operation The operation to do with a session
   * @param <R>       The type of expected result
   * @return The result from the operation
   */
  <R> R ensureSessionFor(SessionOperation<R> operation);

  /**
   * Facility method for type inferenced lambdas that ensures a transaction for the operation.<br>
   * Having an already created transaction object, this method is not needed because #ensureSessionFor()
   * can be used instead. However, this method helps declaring inline lambdas which need type deduction
   * to infer that a transaction is needed and not a session.<br>
   *   By using this method the compiler knows that a transaction type corresponds to the lambda
   *
   * @param operation The operation to execute
   * @param <R>       The type of expected result
   * @return The result of the operation
   */
  <R> R ensureTransactionFor(TransactionOperation<R> operation);

  /**
   * Closes this ORM connections and frees all the resources
   */
  void close();
}
