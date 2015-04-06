package ar.com.tenpines.orm.impl.contexts;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.api.entities.Persistable;
import ar.com.tenpines.orm.api.exceptions.CrudException;
import ar.com.tenpines.orm.api.operations.CrudOperation;
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
    public <R> R doUnderTransaction(TransactionOperation<R> operation) {
        // We are already under the context of a transaction
        return operation.applyUnder(this);
    }

    @Override
    public Session getSession() {
        return sessionContext.getSession();
    }

    @Override
    public void close() {
        sessionContext.close();
    }

    @Override
    public Long save(Persistable instance) throws CrudException {
        return sessionContext.save(instance);
    }

    @Override
    public void delete(Persistable instance) {
        sessionContext.delete(instance);
    }

    @Override
    public <R> Nary<R> perform(CrudOperation<R> operation) {
        return sessionContext.perform(operation);
    }

    public static HibernateTransactionContext create(SessionContext parentContext, Transaction boundTransaction) {
        HibernateTransactionContext context = new HibernateTransactionContext();
        context.sessionContext = parentContext;
        context.currentTransaction = boundTransaction;
        return context;
    }

}
