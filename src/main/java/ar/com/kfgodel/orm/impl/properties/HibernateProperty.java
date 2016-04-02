package ar.com.kfgodel.orm.impl.properties;

import ar.com.kfgodel.nary.api.optionals.Optional;
import ar.com.kfgodel.orm.impl.properties.types.*;
import ar.com.kfgodel.orm.impl.properties.values.Hbm2DdlValue;
import ar.com.kfgodel.orm.impl.properties.values.TimeValue;
import org.hibernate.dialect.Dialect;

import java.util.Properties;

/**
 * This type represents the different hibernate properties used to configure it.<br>
 * This is a subset of all the properties http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch03.html
 * containing only used properties
 * <p/>
 * Created by kfgodel on 21/03/15.
 */
public interface HibernateProperty<T> {

  /**
   * Defines the adapter to talk with the underlying database using the correct SQL syntax
   */
  HibernateProperty<Class<? extends Dialect>> DB_DIALECT = ClassProperty.create("hibernate.dialect");
  /**
   * The JDBC url used to connect to the database
   */
  HibernateProperty<String> DB_URL = StringProperty.create("hibernate.connection.url");
  /**
   * The user to authenticate against the database
   */
  HibernateProperty<String> DB_USERNAME = StringProperty.create("hibernate.connection.username");
  /**
   * The user password to authenticate against the database
   */
  HibernateProperty<String> DB_PASSWORD = StringProperty.create("hibernate.connection.password");

  /**
   * Tries to order updates based on the objects id so the possibility of concurrents deadlocks minimize
   */
  HibernateProperty<Boolean> MISC_ORDER_UPDATES = BooleanProperty.create("hibernate.order_updates");
  /**
   * Uses new id generators for improved performance
   */
  HibernateProperty<Boolean> MISC_ID_GENERATOR_MAPPINGS = BooleanProperty.create("hibernate.id.new_generator_mappings");

  /**
   * Defines the strategy to match class mappings and DB structure
   */
  HibernateProperty<Hbm2DdlValue> MISC_HBM2DDL = Hbm2DdlProperty.create();


  /**
   * Amount of new connections to fetch when needed
   */
  HibernateProperty<Integer> C3P0_ACQUIRE_INCREMENT = IntegerProperty.create("hibernate.c3p0.acquireIncrement");
  /**
   * Minimum size for the pool
   */
  HibernateProperty<Integer> C3P0_MIN_SIZE = IntegerProperty.create("hibernate.c3p0.minPoolSize");
  /**
   * Maximum number of connections in the pool
   */
  HibernateProperty<Integer> C3P0_MAX_SIZE = IntegerProperty.create("hibernate.c3p0.maxPoolSize");
  /**
   * Number of attempts to retry a connection fetching
   */
  HibernateProperty<Integer> C3P0_ACQUIRE_RETRIES = IntegerProperty.create("hibernate.c3p0.acquireRetryAttempts");
  /**
   * Number of seconds to keep an idle connection before closing it on normal operation
   */
  HibernateProperty<TimeValue> C3P0_MAX_IDLE_TIME = SecondProperty.create("hibernate.c3p0.maxIdleTime");
  /**
   * Number of seconds to keep an idle connection during spikes
   */
  HibernateProperty<TimeValue> C3P0_MAX_IDLE_TIME_EXCESS = SecondProperty.create("hibernate.c3p0.maxIdleTimeExcessConnections");

  /**
   * Numbers of milliseconds to wait a new fetched connection
   */
  HibernateProperty<TimeValue> C3P0_CHECKOUT_TIMEOUT = MillisecondProperty.create("hibernate.c3p0.checkoutTimeout");


  /**
   * Sets the typed value for this property in the given properties object
   *
   * @param properties The properties to configure
   * @param value      The value for this property
   */
  void setIn(Properties properties, T value);

  /**
   * Gets the typed value for this property from the given properties object
   * @param properties The properties to get this value from
   * @return null if the propery is missing (as indicated by properties contract)
   */
  Optional<T> getFrom(Properties properties);

}
