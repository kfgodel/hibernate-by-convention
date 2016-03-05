package ar.com.tenpines.orm.api.config;

import org.hibernate.cfg.Configuration;

/**
 * This type represents a hibernate configurator that generates a configuration for a certain use case.<br>
 * For instance a default "by convention" configuration
 *
 * Created by kfgodel on 22/03/15.
 */
public interface HibernateConfigurator {

  /**
   * Creates the configuration for hibernate based on this instance
   *
   * @return The configuration to start hibernate
   */
  Configuration createConfiguration();
}
