package ar.com.tenpines.orm.impl;

import ar.com.tenpines.orm.api.HibernateOrm;
import ar.com.tenpines.orm.api.Preconfig;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.TransactionContext;
import ar.com.tenpines.orm.impl.contexts.HibernateSessionContext;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.function.Function;

/**
 * This class implements an ORM layer using hibernate
 * Created by kfgodel on 22/03/15.
 */
public class HibernateFacade implements HibernateOrm {

    private SessionFactory sessionFactory;

    public static HibernateFacade create(Preconfig appConfig) {
        HibernateFacade hibernateOrm = new HibernateFacade();
        hibernateOrm.initialize(appConfig);
        return hibernateOrm;

    }

    private void initialize(Preconfig appConfig) {
        Configuration hibernateConfig = appConfig.createHibernateConfig();

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                hibernateConfig.getProperties()).build();
        this.sessionFactory = hibernateConfig.buildSessionFactory(serviceRegistry);
    }


    @Override
    public <T> T doWithSession(Function<SessionContext, T> operation) {
        HibernateSessionContext sessionContext = HibernateSessionContext.create(sessionFactory.openSession());
        try{
            return operation.apply(sessionContext);
        }finally {
            sessionContext.close();
        }
    }

    @Override
    public <T> T doInTransaction(Function<TransactionContext, T> operation) {
        return this.doWithSession((sessionContext)-> sessionContext.doInTransaction(operation));
    }

    @Override
    public void close() {
        this.sessionFactory.close();
    }
}
