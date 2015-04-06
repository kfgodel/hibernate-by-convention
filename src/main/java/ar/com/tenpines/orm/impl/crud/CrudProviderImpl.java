package ar.com.tenpines.orm.impl.crud;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.crud.CrudProvider;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.exceptions.CrudException;
import ar.com.tenpines.orm.api.operations.CrudOperation;
import org.hibernate.Session;

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
    public <R> Nary<R> perform(CrudOperation<R> operation) {
        return operation.applyUsing(currentSession);
    }

    public static CrudProviderImpl create(Session session) {
        CrudProviderImpl provider = new CrudProviderImpl();
        provider.currentSession = session;
        return provider;
    }

}
