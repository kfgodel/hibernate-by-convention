package ar.com.kfgodel.orm.api.operations.basic;

import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.entities.Persistable;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import org.hibernate.Session;

/**
 * This type represents a save operation done in the database
 *
 * Created by kfgodel on 05/04/15.
 */
public class Save<T extends Persistable> implements TransactionOperation<T> {

    private T instanceToSave;

    public static <T extends Persistable> Save<T> create(T instanceToSave) {
        Save<T> save = new Save<>();
        save.instanceToSave = instanceToSave;
        return save;
    }

    @Override
    public T applyWithTransactionOn(TransactionContext transactionContext) {
        Session session = transactionContext.getSession();
        session.saveOrUpdate(instanceToSave);
        return instanceToSave;
    }
}
