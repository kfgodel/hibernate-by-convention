package ar.com.kfgodel.orm;

import ar.com.dgarcia.javaspec.api.TestContext;
import ar.com.tenpines.orm.impl.config.DialectDeducer;

import java.util.function.Supplier;

/**
 * Created by kfgodel on 03/03/16.
 */
public interface HibernateTestContext extends TestContext {

  DialectDeducer deducer();
  void deducer(Supplier<DialectDeducer> definition);

}
