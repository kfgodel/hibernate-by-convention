package ar.com.tenpines.orm.impl.config;

import ar.com.kfgodel.convention.api.Convention;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.tenpines.orm.api.config.DbCoordinates;
import ar.com.tenpines.orm.api.config.HibernateConfigurator;
import ar.com.tenpines.orm.impl.properties.HibernateProperty;
import ar.com.tenpines.orm.impl.properties.values.Hbm2DdlValue;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;
import ar.com.tenpines.orm.impl.scanner.EntityClasspathScanner;
import com.google.common.collect.Lists;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This type represents a default hibernate configuration following convention definitions
 * to define pool settings and other miscellaneous properties
 * Created by kfgodel on 21/03/15.
 */
public class ByConventionConfigurator implements HibernateConfigurator {
  public static Logger LOG = LoggerFactory.getLogger(ByConventionConfigurator.class);

  private DbCoordinates coordinates;
  private List<String> persistentPackageNames;

  public static ByConventionConfigurator create(DbCoordinates dbCoordinates) {
    ByConventionConfigurator configurator = new ByConventionConfigurator();
    configurator.coordinates = dbCoordinates;
    configurator.persistentPackageNames = Lists.newArrayList(Convention.create().getPersistentPackageName());
    return configurator;
  }

  /**
   * Creates a new configuration based on this intended usage
   *
   * @return The hibernate configuration prepared for a small app usage (pool settings)
   */
  public Configuration createConfiguration() {
    Configuration hibernateConfig = new Configuration();
    hibernateConfig.setProperties(createConfigProperties());
    addPersistentTypes(hibernateConfig);
    return hibernateConfig;
  }

  private void addPersistentTypes(Configuration hibernateConfig) {
    Set<Class<?>> persistentTypes = getPersistentClasses();
    LOG.info("Found {} persistent types: {}", persistentTypes.size(), persistentTypes);
    for (Class<?> persistentType : persistentTypes) {
      hibernateConfig.addAnnotatedClass(persistentType);
    }
  }

  /**
   * Using the persistent package definition looks for persistent types (annotated with @Entity)
   * @return The set of persistent classes
   */
  public Set<Class<?>> getPersistentClasses() {
    return persistentPackageNames.stream()
        .flatMap(this::discoverPersistentTypesIn)
        .collect(Collectors.toSet());
  }

  private Stream<Class<?>> discoverPersistentTypesIn(String packageName) {
    Set<Class<?>> persistentTypes = EntityClasspathScanner.create(packageName).findPersistentTypes();
    return persistentTypes.stream();
  }

  private Properties createConfigProperties() {
    Properties configProperties = new Properties();

    HibernateProperty.DB_DIALECT.setIn(configProperties, coordinates.getDbDialect());
    HibernateProperty.DB_URL.setIn(configProperties, coordinates.getDbUrl());
    HibernateProperty.DB_USERNAME.setIn(configProperties, coordinates.getDbUsername());
    HibernateProperty.DB_PASSWORD.setIn(configProperties, coordinates.getDbPassword());

    HibernateProperty.MISC_ORDER_UPDATES.setIn(configProperties, true);
    HibernateProperty.MISC_ID_GENERATOR_MAPPINGS.setIn(configProperties, true);
    HibernateProperty.MISC_HBM2DDL.setIn(configProperties, Hbm2DdlValue.UPDATE);

    HibernateProperty.C3P0_MIN_SIZE.setIn(configProperties, 0);
    HibernateProperty.C3P0_MAX_SIZE.setIn(configProperties, 50);
    HibernateProperty.C3P0_ACQUIRE_INCREMENT.setIn(configProperties, 2);
    HibernateProperty.C3P0_ACQUIRE_RETRIES.setIn(configProperties, 5);
    HibernateProperty.C3P0_MAX_IDLE_TIME.setIn(configProperties, TimeValue.create(30, TimeUnit.MINUTES));
    HibernateProperty.C3P0_MAX_IDLE_TIME_EXCESS.setIn(configProperties, TimeValue.create(5, TimeUnit.MINUTES));
    HibernateProperty.C3P0_CHECKOUT_TIMEOUT.setIn(configProperties, TimeValue.create(30, TimeUnit.SECONDS));
    return configProperties;
  }

  public Nary<String> getPersistentPackageNames() {
    return NaryFromNative.create(persistentPackageNames.stream());
  }

  public void changePersistentPackages(String... packages) {
    this.persistentPackageNames = Arrays.asList(packages);
  }
}
