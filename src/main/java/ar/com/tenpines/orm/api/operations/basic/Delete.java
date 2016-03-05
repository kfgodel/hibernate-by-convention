package ar.com.tenpines.orm.api.operations.basic;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.entities.Persistable;
import ar.com.tenpines.orm.api.operations.SessionOperation;
import org.hibernate.Session;

/**
 * This type represents the crud operation of deleting a persistent instance
 * Created by kfgodel on 05/04/15.
 */
public class Delete implements SessionOperation<Nary<Void>> {

    private Persistable instance;

    public static Delete create(Persistable instance) {
        Delete delete = new Delete();
        delete.instance = instance;
        return delete;
    }

    @Override
    public Nary<Void> applyWithSessionOn(SessionContext sessionContext) {
        Session session = sessionContext.getSession();
        session.delete(instance);
        return NaryFromNative.empty();
    }
}
