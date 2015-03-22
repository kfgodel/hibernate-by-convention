package ar.com.tenpines.orm.impl.contexts;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Function;

/**
 * This type implements the transaction context with a reference to the current transaction
 * Created by kfgodel on 22/03/15.
 */
public class HibernateTransactionContext implements TransactionContext {

    private SessionContext sessionContext;
    private Transaction currentTransaction;

    @Override
    public Transaction getTransaction() {
        return currentTransaction;
    }

    @Override
    public void commit() {
        getTransaction().commit();
    }

    @Override
    public void rollback() {
        getTransaction().rollback();
    }

    @Override
    public <T> T doInTransaction(Function<TransactionContext, T> operation) {
        return sessionContext.doInTransaction(operation);
    }

    @Override
    public Session getSession() {
        return sessionContext.getSession();
    }

    @Override
    public void close() {
        sessionContext.close();
    }

    public static HibernateTransactionContext create(SessionContext parentContext, Transaction boundTransaction) {
        HibernateTransactionContext context = new HibernateTransactionContext();
        context.sessionContext = parentContext;
        context.currentTransaction = boundTransaction;
        return context;
    }

}
