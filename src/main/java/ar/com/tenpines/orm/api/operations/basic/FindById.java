package ar.com.tenpines.orm.api.operations.basic;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.entities.Persistable;
import ar.com.tenpines.orm.api.operations.SessionOperation;
import org.hibernate.Session;

/**
 * This type represents a get by id operation in the database
 * Created by kfgodel on 04/04/15.
 */
public class FindById<T extends Persistable> implements SessionOperation<Nary<T>> {

    private Class<T> persistentType;
    private Long expectedId;

    public static<T extends Persistable> FindById<T> create(Class<T> type, Long id) {
        FindById<T> findById = new FindById<>();
        findById.persistentType = type;
        findById.expectedId = id;
        return findById;
    }

    @Override
    public Nary<T> applyWithSessionOn(SessionContext sessionContext) {
        Session session = sessionContext.getSession();
        T foundObject = (T) session.get(persistentType, expectedId);
        return NaryFromNative.ofNullable(foundObject);
    }
}
