package ar.com.tenpines.orm.impl.properties;

import ar.com.tenpines.orm.impl.properties.types.*;
import org.hibernate.dialect.Dialect;

/**
 * This type represents the different hibernate properties used configure it.<br>
 *     This is a subset of all the properties http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/ch03.html
 *     containing only used properties
 *
 * Created by kfgodel on 21/03/15.
 */
public class HibernateProperty {

    /**
     * Defines the adapter to talk with the underlying database using the correct SQL syntax
     */
    public static final ClassProperty<Dialect> DB_DIALECT = ClassProperty.create("hibernate.dialect");
    /**
     * The JDBC url used to connect to the database
     */
    public static final StringProperty DB_URL = StringProperty.create("hibernate.connection.url");
    /**
     * The user to authenticate against the database
     */
    public static final StringProperty DB_USERNAME = StringProperty.create("hibernate.connection.username");
    /**
     * The user password to authenticate against the database
     */
    public static final StringProperty DB_PASSWORD = StringProperty.create("hibernate.connection.password");

    /**
     * Tries to order updates based on the objects id so the possibility of concurrents deadlocks minimize
     */
    public static final BooleanProperty MISC_ORDER_UPDATES = BooleanProperty.create("hibernate.order_updates");
    /**
     * Uses new id generators for improved performance
     */
    public static final BooleanProperty MISC_ID_GENERATOR_MAPPINGS = BooleanProperty.create ("hibernate.id.new_generator_mappings");

    /**
     * Defines the strategy to match class mappings and DB structure
     */
    public static final Hbm2DdlProperty MISC_HBM2DDL = Hbm2DdlProperty.create();


    /**
     * Amount of new connections to fetch when needed
     */
    public static final IntegerProperty C3P0_ACQUIRE_INCREMENT = IntegerProperty.create("hibernate.c3p0.acquireIncrement");
    /**
     * Minimum size for the pool
     */
    public static final IntegerProperty C3P0_MIN_SIZE = IntegerProperty.create("hibernate.c3p0.minPoolSize");
    /**
     * Maximum number of connections in the pool
     */
    public static final IntegerProperty C3P0_MAX_SIZE = IntegerProperty.create("hibernate.c3p0.maxPoolSize");
    /**
     * Number of attempts to retry a connection fetching
     */
    public static final IntegerProperty C3P0_ACQUIRE_RETRIES = IntegerProperty.create("hibernate.c3p0.acquireRetryAttempts");
    /**
     * Number of seconds to keep an idle connection before closing it on normal operation
     */
    public static final SecondProperty C3P0_MAX_IDLE_TIME = SecondProperty.create("hibernate.c3p0.maxIdleTime");
    /**
     * Number of seconds to keep an idle connection during spikes
     */
    public static final SecondProperty C3P0_MAX_IDLE_TIME_EXCESS = SecondProperty.create("hibernate.c3p0.maxIdleTimeExcessConnections");

    /**
     * Numbers of milliseconds to wait a new fetched connection
     */
    public static final MillisecondProperty C3P0_CHECKOUT_TIMEOUT = MillisecondProperty.create("hibernate.c3p0.checkoutTimeout");


}
