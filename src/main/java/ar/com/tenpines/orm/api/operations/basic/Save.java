package ar.com.tenpines.orm.api.operations.basic;

import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.operations.TransactionOperation;

/**
 * This type represents a save operation done in the database
 *
 * Created by kfgodel on 05/04/15.
 */
public class Save implements TransactionOperation<Long> {
    private Identifiable instanceToSave;

    @Override
    public Long applyUnder(TransactionContext transactionContext) {
        return transactionContext.save(instanceToSave);
    }

    public static Save create(Identifiable instanceTosave) {
        Save save = new Save();
        save.instanceToSave = instanceTosave;
        return save;
    }

}
