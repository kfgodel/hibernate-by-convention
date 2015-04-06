package ar.com.tenpines.orm.api.operations.basic;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.entities.Persistable;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;
import java.util.function.Function;

/**
 * This type represents the database operation that fetches all the instances of a given type.<br>
 *     It orders the results by id (lacking a better way to order them)
 * Created by kfgodel on 04/04/15.
 */
public class FindAll<T extends Identifiable> implements Function<Session, Nary<T>> {

    private Class<T> persistentType;

    @Override
    public Nary<T> apply(Session session) {
        Criteria criteria = session.createCriteria(persistentType);
        criteria.addOrder(Order.asc("id"));
        List<T> list = criteria.list();
        return NaryFromNative.create(list.stream());
    }

    public static<T extends Persistable> FindAll<T> of(Class<T> persistentType) {
        FindAll<T> allOf = new FindAll<>();
        allOf.persistentType = persistentType;
        return allOf;
    }

}
