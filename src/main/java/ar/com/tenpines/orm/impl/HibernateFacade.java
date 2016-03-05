package ar.com.tenpines.orm.impl;

import ar.com.tenpines.orm.api.DbCoordinates;
import ar.com.tenpines.orm.api.HibernateConfigurator;
import ar.com.tenpines.orm.api.HibernateOrm;
import ar.com.tenpines.orm.api.SessionContext;
import ar.com.tenpines.orm.api.operations.SessionOperation;
import ar.com.tenpines.orm.api.operations.TransactionOperation;
import ar.com.tenpines.orm.impl.config.ByConventionConfigurator;
import ar.com.tenpines.orm.impl.contexts.SessionContextualizer;
import ar.com.tenpines.orm.impl.contexts.HibernateSessionContext;
import ar.com.tenpines.orm.impl.contexts.ThreadLocalContextualizer;
import com.tenpines.integration.hibernate.events.TimeStamperEventListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

/**
 * This class implements an ORM layer using hibernate
 * Created by kfgodel on 22/03/15.
 */
public class HibernateFacade implements HibernateOrm {

  private static final ThreadLocal<SessionContext> sessionContextPerThread = new ThreadLocal<>();
  private SessionFactory sessionFactory;
  private SessionContextualizer<SessionContext> sessionContextualizer;

  public static HibernateFacade create(HibernateConfigurator configurator) {
    HibernateFacade hibernateOrm = new HibernateFacade();
    hibernateOrm.initialize(configurator);
    return hibernateOrm;
  }

  /**
   * Creates a new hibernate facada using conventions for the configuration
   * @param dbCoordinates The database coordinates to open connections
   * @return The created facade
   */
  public static HibernateOrm createWithConventionsFor(DbCoordinates dbCoordinates) {
    HibernateConfigurator configurator = ByConventionConfigurator.create(dbCoordinates);
    return create(configurator);
  }


  private void initialize(HibernateConfigurator configurator) {
    Configuration hibernateConfig = configurator.createConfiguration();

    StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
      .applySettings(hibernateConfig.getProperties())
      .build();

    this.sessionFactory = hibernateConfig.buildSessionFactory(serviceRegistry);
    registerTimestamper();

    this.sessionContextualizer = ThreadLocalContextualizer.create(()->
      HibernateSessionContext.create(this.sessionFactory.openSession())
    , sessionContextPerThread);
  }

  /**
   * Adds the timestamper event listener to set creation and update dates to entities
   */
  private void registerTimestamper() {
    // Hack to register our event listeners. There's no easy way to do it programmatically
    // The other approach is to user a META-INF integrator file
    TimeStamperEventListener timeStamper = TimeStamperEventListener.create();
    EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry()
      .getService(EventListenerRegistry.class);
    registry.appendListeners(EventType.PRE_INSERT, timeStamper);
    registry.appendListeners(EventType.PRE_UPDATE, timeStamper);
  }


  @Override
  public <R> R ensureSessionFor(SessionOperation<R> operation) {
    return this.sessionContextualizer.ensureContextFor(operation::applyWithSessionOn);
  }

  @Override
  public <R> R ensureTransactionFor(TransactionOperation<R> operation) {
    // The transaction operation is a session, so no need to do anything fancy here
    return this.ensureSessionFor(operation);
  }

  @Override
  public void close() {
    this.sessionFactory.close();
  }

}
