package ar.com.tenpines.orm.api;

import org.hibernate.Transaction;

/**
 * This type represents the reification of the context for an operation that needs a transaction.<br>
 * Instances of this type can be used to access transaction bound operations
 * Created by kfgodel on 22/03/15.
 */
public interface TransactionContext extends SessionContext {

  /**
   * @return The current transaction
   */
  Transaction getTransaction();

  /**
   * Commits the current transaction
   */
  void commit();

  /**
   * Rolls back the current transaction
   */
  void rollback();

}
