package ar.com.kfgodel.orm.impl.contexts;

import ar.com.kfgodel.orm.api.SessionContext;
import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import org.hibernate.Session;

/**
 * This type implements the session context by holding a reference to the current session
 * Created by kfgodel on 22/03/15.
 */
public class HibernateSessionContext implements SessionContext {

  private static final ThreadLocal<TransactionContext> transactionContextPerThread = new ThreadLocal<>();

  private Session currentSession;
  private SessionContextualizer<TransactionContext> transactionContextualizer;

  @Override
  public <R> R doUnderTransaction(TransactionOperation<R> operation) {
    return this.transactionContextualizer.ensureContextFor((transactionContext) ->
      transactionContext.doUnderTransaction(operation)
    );
  }

  @Override
  public Session getSession() {
    return currentSession;
  }

  @Override
  public void close() {
    getSession().close();
  }

  public static HibernateSessionContext create(Session boundSession) {
    HibernateSessionContext sessionContext = new HibernateSessionContext();
    sessionContext.initialize(boundSession);
    return sessionContext;
  }

  private void initialize(Session boundSession) {
    this.currentSession = boundSession;
    this.transactionContextualizer = ThreadLocalContextualizer.create(() ->
        HibernateTransactionContext.create(this, boundSession.beginTransaction())
      , transactionContextPerThread);
  }

}
