package ar.com.kfgodel.orm.api.operations.basic;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.entities.Persistable;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import org.hibernate.Session;

/**
 * This type represents the crud operation of deleting a persistent instance
 * Created by kfgodel on 05/04/15.
 */
public class Delete implements TransactionOperation<Nary<Void>> {

    private Persistable instance;

    public static Delete create(Persistable instance) {
        Delete delete = new Delete();
        delete.instance = instance;
        return delete;
    }

    @Override
    public Nary<Void> applyWithTransactionOn(TransactionContext transactionContext) {
        Session session = transactionContext.getSession();
        session.delete(instance);
        return Nary.empty();
    }
}
