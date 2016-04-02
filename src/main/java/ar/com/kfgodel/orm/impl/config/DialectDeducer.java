package ar.com.kfgodel.orm.impl.config;

import org.hibernate.dialect.Dialect;

/**
 * This type represents a deducer object that can deduct which hibernate dialect corresponds to
 * a given jdbc database url
 * <p>
 * Created by kfgodel on 03/03/16.
 */
public interface DialectDeducer {
  /**
   * Tries to deduct the correct dialect for a given jdb url.<br>
   * If more than one dialect is available for a given database, the latests is chosen.<br>
   * Unknown dialects are rejected as an exception IllegalArgumentException
   *
   * @param jdbcUrl The url to analyze
   * @return The matching dialect
   */
  Class<? extends Dialect> deductDialectFor(String jdbcUrl);
}
