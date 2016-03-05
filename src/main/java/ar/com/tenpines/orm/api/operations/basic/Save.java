package ar.com.tenpines.orm.api.operations.basic;

import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.entities.Persistable;
import ar.com.tenpines.orm.api.operations.SessionOperation;
import org.hibernate.Session;

/**
 * This type represents a save operation done in the database
 *
 * Created by kfgodel on 05/04/15.
 */
public class Save implements SessionOperation<Long> {

    private Persistable instanceToSave;

    public static Save create(Persistable instanceToSave) {
        Save save = new Save();
        save.instanceToSave = instanceToSave;
        return save;
    }

    @Override
    public Long applyWithSessionOn(SessionContext sessionContext) {
        Session session = sessionContext.getSession();
        session.saveOrUpdate(instanceToSave);
        return instanceToSave.getId();
    }
}
