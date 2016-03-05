package ar.com.kfgodel.orm;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.tenpines.orm.api.config.DbCoordinates;
import ar.com.tenpines.orm.impl.config.ByConventionConfigurator;
import ar.com.tenpines.orm.impl.config.DialectDeducer;

import java.util.Properties;
import java.util.function.Supplier;

/**
 * Created by kfgodel on 03/03/16.
 */
public interface HibernateTestContext extends TestContext {

  DialectDeducer deducer();
  void deducer(Supplier<DialectDeducer> definition);

  ByConventionConfigurator configurator();
  void configurator(Supplier<ByConventionConfigurator> definition);

  DbCoordinates coordinates();
  void coordinates(Supplier<DbCoordinates> definition);

  Properties properties();
  void properties(Supplier<Properties> definition);

}
