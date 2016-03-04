package ar.com.kfgodel.orm;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.orm.testentities.PersistentTestExample;
import ar.com.tenpines.orm.impl.config.ByConventionConfigurator;
import ar.com.tenpines.orm.impl.config.ImmutableDbCoordinates;
import ar.com.tenpines.orm.impl.properties.HibernateProperty;
import ar.com.tenpines.orm.impl.properties.values.Hbm2DdlValue;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type tests the correct configuration by convention
 * Created by kfgodel on 04/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class ByconventionConfiguratorTest extends JavaSpec<HibernateTestContext> {
  @Override
  public void define() {
    describe("a convention configurator", () -> {
      context().configurator(()->
        ByConventionConfigurator.create(context().coordinates())
      );
      context().coordinates(()-> ImmutableDbCoordinates.create(H2Dialect.class, "le url", "le user", "le pass"));

      it("creates a hibernate configuration",()->{
        Configuration hibernateConfig = context().configurator().createConfiguration();
        assertThat(hibernateConfig).isNotNull();
      });

      describe("persistent packages", () -> {

        it("defaults to conventional package",()->{
          List<String> packageNames = context().configurator().getPersistentPackageNames()
            .collect(Collectors.toList());
          assertThat(packageNames).containsExactly("convention.persistent");
        });
        
        it("can be overriden with other packages",()->{
          context().configurator().changePersistentPackages("ar.com.kfgodel.orm.testentities", "other.package");

          List<String> packageNames = context().configurator().getPersistentPackageNames()
            .collect(Collectors.toList());

          assertThat(packageNames).containsExactly("ar.com.kfgodel.orm.testentities", "other.package");
        });   
      });

      it("is used to discover persistent types (annotated with @entity)",()->{
        context().configurator().changePersistentPackages("ar.com.kfgodel.orm.testentities");

        Set<Class<?>> persistentClasses = context().configurator().getPersistentClasses();

        assertThat(persistentClasses).containsExactly(PersistentTestExample.class);
      });   

      describe("configuration properties", () -> {
        context().properties(()-> context().configurator().createConfiguration().getProperties());

        it("includes the db coordinates",()->{
          assertThat(HibernateProperty.DB_DIALECT.getFrom(context().properties()).get())
            .isEqualTo(H2Dialect.class);
          assertThat(HibernateProperty.DB_URL.getFrom(context().properties()).get())
            .isEqualTo("le url");
          assertThat(HibernateProperty.DB_USERNAME.getFrom(context().properties()).get())
            .isEqualTo("le user");
          assertThat(HibernateProperty.DB_PASSWORD.getFrom(context().properties()).get())
            .isEqualTo("le pass");
        });

        it("includes pool connection settings",()->{
          assertThat(HibernateProperty.C3P0_MIN_SIZE.getFrom(context().properties()).get())
            .isEqualTo(0);
          assertThat(HibernateProperty.C3P0_MAX_SIZE.getFrom(context().properties()).get())
            .isEqualTo(50);
          assertThat(HibernateProperty.C3P0_ACQUIRE_INCREMENT.getFrom(context().properties()).get())
            .isEqualTo(2);
          assertThat(HibernateProperty.C3P0_ACQUIRE_RETRIES.getFrom(context().properties()).get())
            .isEqualTo(5);
          assertThat(HibernateProperty.C3P0_MAX_IDLE_TIME.getFrom(context().properties()).get())
            .isEqualTo(TimeValue.create(30, TimeUnit.MINUTES));
          assertThat(HibernateProperty.C3P0_MAX_IDLE_TIME_EXCESS.getFrom(context().properties()).get())
            .isEqualTo(TimeValue.create(5, TimeUnit.MINUTES));
          assertThat(HibernateProperty.C3P0_CHECKOUT_TIMEOUT.getFrom(context().properties()).get())
            .isEqualTo(TimeValue.create(30, TimeUnit.SECONDS));
        });

        it("incudes hibernate specific settings",()->{
          assertThat(HibernateProperty.MISC_ORDER_UPDATES.getFrom(context().properties()).get())
            .isEqualTo(true);
          assertThat(HibernateProperty.MISC_ID_GENERATOR_MAPPINGS.getFrom(context().properties()).get())
            .isEqualTo(true);
          assertThat(HibernateProperty.MISC_HBM2DDL.getFrom(context().properties()).get())
            .isEqualTo(Hbm2DdlValue.UPDATE);
        });
        
        it("can be overriden by custom values",()->{
        });
      });

    });

  }
}