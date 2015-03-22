package ar.com.tenpines.orm.impl.contexts;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type implements the session context by holding a reference to the current session
 * Created by kfgodel on 22/03/15.
 */
public class HibernateSessionContext implements SessionContext {

    private Session currentSession;

    @Override
    public <T> T doInTransaction(Function<TransactionContext, T> operation) {
        HibernateTransactionContext transactionContext = HibernateTransactionContext.create(this, getSession().beginTransaction());
        boolean finishedSuccessfully = false;
        try {
            T result = operation.apply(transactionContext);
            finishedSuccessfully = true;
            return result;
        }finally {
            if(finishedSuccessfully) {
                transactionContext.commit();
            }else {
                transactionContext.rollback();
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

    public static HibernateSessionContext create(Session boundSession) {
        HibernateSessionContext sessionContext = new HibernateSessionContext();
        sessionContext.currentSession = boundSession;
        return sessionContext;
    }

}
