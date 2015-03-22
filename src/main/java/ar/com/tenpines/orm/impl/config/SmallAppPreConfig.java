package ar.com.tenpines.orm.impl.config;

import ar.com.tenpines.orm.api.DbCoordinates;
import ar.com.tenpines.orm.api.Preconfig;
import ar.com.tenpines.orm.impl.properties.HibernateProperty;
import ar.com.tenpines.orm.impl.properties.values.Hbm2DdlValue;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;
import ar.com.tenpines.orm.impl.scanner.EntityClasspathScanner;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * This type represents a default hibernate configuration for smallish apps
 * Created by kfgodel on 21/03/15.
 */
public class SmallAppPreConfig implements Preconfig {
    public static Logger LOG = LoggerFactory.getLogger(SmallAppPreConfig.class);

    private DbCoordinates coordinates;
    private Set<Class<?>> persistentTypes;

    public static SmallAppPreConfig create(DbCoordinates dbCoordinates, String persistentRootPackage) {
        Set<Class<?>> persistentTypes = EntityClasspathScanner.create(persistentRootPackage).findPersistentTypes();
        LOG.info("Found {} persistent types: {}", persistentTypes.size(), persistentTypes);

        SmallAppPreConfig smallAppHibernateConfig = new SmallAppPreConfig();
        smallAppHibernateConfig.coordinates = dbCoordinates;
        smallAppHibernateConfig.persistentTypes = persistentTypes;
        return smallAppHibernateConfig;
    }

    /**
     * Creates a new configuration based on this intended usage
     * @return The hibernate configuration prepared for a small app usage (pool settings)
     */
    public Configuration createHibernateConfig() {
        Properties configProperties = new Properties();

        HibernateProperty.DB_DIALECT.setIn(configProperties, coordinates.getDbDialect());
        HibernateProperty.DB_URL.setIn(configProperties, coordinates.getDbUrl());
        HibernateProperty.DB_USERNAME.setIn(configProperties, coordinates.getDbUsername());
        HibernateProperty.DB_PASSWORD.setIn(configProperties, coordinates.getDbPassword());

        HibernateProperty.MISC_ORDER_UPDATES.setIn(configProperties, true);
        HibernateProperty.MISC_ID_GENERATOR_MAPPINGS.setIn(configProperties, true);
        HibernateProperty.MISC_HBM2DDL.setIn(configProperties, Hbm2DdlValue.UPDATE);

        HibernateProperty.C3P0_MIN_SIZE.setIn(configProperties, 0);
        HibernateProperty.C3P0_MAX_SIZE.setIn(configProperties, 20);
        HibernateProperty.C3P0_ACQUIRE_INCREMENT.setIn(configProperties, 1);
        HibernateProperty.C3P0_ACQUIRE_RETRIES.setIn(configProperties, 5);
        HibernateProperty.C3P0_MAX_IDLE_TIME.setIn(configProperties, TimeValue.create(30, TimeUnit.MINUTES));
        HibernateProperty.C3P0_MAX_IDLE_TIME_EXCESS.setIn(configProperties, TimeValue.create(5, TimeUnit.MINUTES));
        HibernateProperty.C3P0_CHECKOUT_TIMEOUT.setIn(configProperties, TimeValue.create(30, TimeUnit.SECONDS));


        Configuration hibernateConfig = new Configuration();
        hibernateConfig.setProperties(configProperties);
        for (Class<?> persistentType : persistentTypes) {
            hibernateConfig.addAnnotatedClass(persistentType);
        }
        return hibernateConfig;
    }
}
