package ar.com.tenpines.orm.api;

import org.hibernate.cfg.Configuration;

/**
 * This type represents a predefined configuration for hibernate based on certain assumed app usage
 * Created by kfgodel on 22/03/15.
 */
public interface Preconfig {

    /**
     * Creates the configuration for hibernate based on this instance
     * @return The configuration to start hibernate
     */
    Configuration createHibernateConfig();
}
