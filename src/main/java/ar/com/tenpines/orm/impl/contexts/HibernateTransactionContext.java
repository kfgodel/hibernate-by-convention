package ar.com.tenpines.orm.impl.contexts;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.api.exceptions.OrmException;
import ar.com.tenpines.orm.api.operations.TransactionOperation;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * This type implements the transaction context with a reference to the current transaction
 * Created by kfgodel on 22/03/15.
 */
public class HibernateTransactionContext implements TransactionContext {

  private SessionContext sessionContext;
  private Transaction currentTransaction;
  private boolean alreadyCommited;
  private boolean alreadyRollbacked;

  @Override
  public Transaction getTransaction() {
    return currentTransaction;
  }

  @Override
  public void commit() {
    if (alreadyCommited) {
      throw new OrmException("The transaction was already commited. It can't be committed again");
    }
    getTransaction().commit();
    alreadyCommited = true;
  }

  @Override
  public void rollback() {
    if (alreadyRollbacked) {
      throw new OrmException("The transaction was already rollbacked. It can't be rollbacked again");
    }
    getTransaction().rollback();
    alreadyRollbacked = true;
  }

  @Override
  public <R> R doUnderTransaction(TransactionOperation<R> operation) {
    if(transactionIsAlreadyClosed()){
      throw new OrmException("The transaction has been already committed or rollbacked. It cannot execute other operation");
    }
    boolean operationFailed = true;
    try {
      R result = operation.applyWithTransactionOn(this);
      operationFailed = false;
      return result;
    } finally {
      if (operationFailed) {
        this.rollback();
      }
    }
  }

  @Override
  public Session getSession() {
    return sessionContext.getSession();
  }

  @Override
  public void close() {
    if (needsToBeCommited()) {
      this.commit();
    }
  }

  /**
   * Indicates if this transaction context needs to commit before closing
   *
   * @return false if the transaction was already committed or rollbacked
   */
  private boolean needsToBeCommited() {
    return !transactionIsAlreadyClosed();
  }

  /**
   * Indicates if this transaction has been closed and cannot accept new operations
   * @return true if it has been committed or rollbacked
   */
  private boolean transactionIsAlreadyClosed() {
    return alreadyCommited || alreadyRollbacked;
  }

  public static HibernateTransactionContext create(SessionContext parentContext, Transaction boundTransaction) {
    HibernateTransactionContext context = new HibernateTransactionContext();
    context.sessionContext = parentContext;
    context.currentTransaction = boundTransaction;
    return context;
  }

}
