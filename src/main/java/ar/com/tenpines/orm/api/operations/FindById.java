package ar.com.tenpines.orm.api.operations;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type represents a get by id operation in the database
 * Created by kfgodel on 04/04/15.
 */
public class FindById<T> implements Function<Session,Nary<T>> {

    private Class<T> persistentType;
    private Long expectedId;

    @Override
    public Nary<T> apply(Session session) {
        Object foundObject = session.get(persistentType, expectedId);
        return NaryFromNative.ofNullable((T) foundObject);
    }

    public static<T> FindById<T> create(Class<T> type, Long id) {
        FindById<T> findById = new FindById<>();
        findById.persistentType = type;
        findById.expectedId = id;
        return findById;
    }

}
