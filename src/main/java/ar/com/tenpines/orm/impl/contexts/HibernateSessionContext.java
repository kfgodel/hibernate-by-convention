package ar.com.tenpines.orm.impl.contexts;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.api.crud.CrudProvider;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.exceptions.CrudException;
import ar.com.tenpines.orm.impl.crud.CrudProviderImpl;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type implements the session context by holding a reference to the current session
 * Created by kfgodel on 22/03/15.
 */
public class HibernateSessionContext implements SessionContext {

    private static final ThreadLocal<HibernateTransactionContext> currentTransaction = new ThreadLocal<>();

    private Session currentSession;
    private CrudProvider crudProvider;

    @Override
    public <T> T doInTransaction(Function<TransactionContext, T> operation) {
        HibernateTransactionContext existingTransaction = currentTransaction.get();
        if(existingTransaction != null){
            // There's one we can reuse. Other stack entry is responsible for managing it
            return operation.apply(existingTransaction);
        }

        // We need to create and manage a new one
        HibernateTransactionContext newTransaction = HibernateTransactionContext.create(this, getSession().beginTransaction());
        currentTransaction.set(newTransaction);
        boolean finishedSuccessfully = false;
        try {
            T result = operation.apply(newTransaction);
            finishedSuccessfully = true;
            return result;
        }finally {
            currentTransaction.remove();
            if(finishedSuccessfully) {
                newTransaction.commit();
            }else {
                newTransaction.rollback();
            }
        }
    }

    @Override
    public Session getSession() {
        return currentSession;
    }

    @Override
    public void close() {
        getSession().close();
    }

    public CrudProvider getCrudProvider() {
        if (crudProvider == null) {
            crudProvider = CrudProviderImpl.create(getSession());
        }
        return crudProvider;
    }

    @Override
    public Long save(Identifiable instance) throws CrudException {
        return getCrudProvider().save(instance);
    }

    @Override
    public void delete(Identifiable instance) {
        getCrudProvider().delete(instance);
    }

    @Override
    public <T> Nary<T> perform(Function<Session, Nary<T>> operation) {
        return getCrudProvider().perform(operation);
    }

    public static HibernateSessionContext create(Session boundSession) {
        HibernateSessionContext sessionContext = new HibernateSessionContext();
        sessionContext.currentSession = boundSession;
        return sessionContext;
    }

}
