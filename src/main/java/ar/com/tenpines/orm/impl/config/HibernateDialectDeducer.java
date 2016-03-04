package ar.com.tenpines.orm.impl.config;

import org.hibernate.dialect.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the hibernate dialect deducer
 * Created by kfgodel on 03/03/16.
 */
public class HibernateDialectDeducer implements DialectDeducer {

  private Map<String, Class<? extends Dialect>> urlPortionPerDialect;

  public static HibernateDialectDeducer create() {
    HibernateDialectDeducer deducer = new HibernateDialectDeducer();
    deducer.urlPortionPerDialect = new HashMap<>();
    deducer.initialize();
    return deducer;
  }

  private void initialize() {
    this.urlPortionPerDialect.put("h2", H2Dialect.class);
    this.urlPortionPerDialect.put("hsqldb", HSQLDialect.class);
    this.urlPortionPerDialect.put("postgresql", PostgreSQL9Dialect.class);
    this.urlPortionPerDialect.put("mysql", MySQLDialect.class);
    this.urlPortionPerDialect.put("sqlserver", SQLServer2008Dialect.class);
  }

  @Override
  public Class<? extends Dialect> deductDialectFor(String jdbcUrl) {
    return urlPortionPerDialect.entrySet().stream()
      .filter((entry) -> jdbcUrl.startsWith("jdbc:" + entry.getKey() + ":"))
      .map((entry)-> entry.getValue())
      .findFirst()
      .orElseThrow(()-> new IllegalArgumentException("No dialect can be deducted for: " + jdbcUrl));
  }
}
