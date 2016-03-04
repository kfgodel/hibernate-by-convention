package ar.com.kfgodel.orm;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.tenpines.orm.impl.config.HibernateDialectDeducer;
import org.hibernate.dialect.*;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * Test dialect deductions
 *
 * Created by kfgodel on 03/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class DialectDeducerTest extends JavaSpec<HibernateTestContext> {
  @Override
  public void define() {
    describe("a dialect deducer", () -> {
      context().deducer(HibernateDialectDeducer::create);

      it("can deduct an H2 dialect from the jdbc url",()->{
        String jdbcUrl = "jdbc:h2:file:./db/h2";
        Class<? extends Dialect> deducedDialect = context().deducer().deductDialectFor(jdbcUrl);
        assertThat(deducedDialect).isEqualTo(H2Dialect.class);
      });


      it("can deduct an HSQLDB dialect from the jdbc url",()->{
        String jdbcUrl = "jdbc:hsqldb:hsql://localhost:9001";
        Class<? extends Dialect> deducedDialect = context().deducer().deductDialectFor(jdbcUrl);
        assertThat(deducedDialect).isEqualTo(HSQLDialect.class);
      });

      it("can deduct a postgres dialect from the jdbc url",()->{
        String jdbcUrl = "jdbc:postgresql://localhost:5432/hibernatedb";
        Class<? extends Dialect> deducedDialect = context().deducer().deductDialectFor(jdbcUrl);
        assertThat(deducedDialect).isEqualTo(PostgreSQL9Dialect.class);
      });

      it("can deduct a mySQL dialect from the jdbc url",()->{
        String jdbcUrl = "jdbc:mysql://localhost/test";
        Class<? extends Dialect> deducedDialect = context().deducer().deductDialectFor(jdbcUrl);
        assertThat(deducedDialect).isEqualTo(MySQLDialect.class);
      });

      it("can deduct a SQLServer dialect from the jdbc url",()->{
        String jdbcUrl = "jdbc:sqlserver://localhost;databaseName=testDb";
        Class<? extends Dialect> deducedDialect = context().deducer().deductDialectFor(jdbcUrl);
        assertThat(deducedDialect).isEqualTo(SQLServer2008Dialect.class);
      });

      it("throws an error if no dialect can be deducted",()->{
          try{
            context().deducer().deductDialectFor("bogus jdb");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
          }catch(IllegalArgumentException e){
            assertThat(e).hasMessage("No dialect can be deducted for: bogus jdb");
          }
      });   
    });

  }
}