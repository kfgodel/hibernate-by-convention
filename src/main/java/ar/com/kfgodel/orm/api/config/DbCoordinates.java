package ar.com.kfgodel.orm.api.config;

import org.hibernate.dialect.Dialect;

/**
 * This type represents the basic DB information hibernate needs to connect to a database
 * Created by kfgodel on 22/03/15.
 */
public interface DbCoordinates {

    /**
     * @return The type that adapts hibernate to specific sql syntax
     */
    Class<? extends Dialect> getDbDialect();

    /**
     * @return The jdbc url to connect to the base
     */
    String getDbUrl();

    /**
     * @return The user to authenticate
     */
    String getDbUsername();

    /**
     * @return The password to authenticate the user
     */
    String getDbPassword();
}
