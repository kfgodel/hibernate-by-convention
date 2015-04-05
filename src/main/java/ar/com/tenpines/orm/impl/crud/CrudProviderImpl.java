package ar.com.tenpines.orm.impl.crud;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.crud.CrudProvider;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.exceptions.CrudException;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type is ths implementation of a crud provider using hibernate
 *
 * Created by kfgodel on 04/04/15.
 */
public class CrudProviderImpl implements CrudProvider {

    private Session currentSession;

    @Override
    public Long save(Identifiable instance) throws CrudException {
        currentSession.saveOrUpdate(instance);
        return instance.getId();
    }

    @Override
    public void delete(Identifiable instance) {
        currentSession.delete(instance);
    }

    @Override
    public <T> Nary<T> perform(Function<Session, Nary<T>> operation) {
        return operation.apply(currentSession);
    }

    public static CrudProviderImpl create(Session session) {
        CrudProviderImpl provider = new CrudProviderImpl();
        provider.currentSession = session;
        return provider;
    }

}
