package ar.com.tenpines.orm.impl.config;

import ar.com.tenpines.orm.api.DbCoordinates;
import org.hibernate.dialect.Dialect;

/**
 * This type represents the basic DB information hibernate needs to connect to a database
 * Created by kfgodel on 21/03/15.
 */
public class ImmutableDbCoordinates implements DbCoordinates {

    private Class<? extends Dialect> dbDialect;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;

    public Class<? extends Dialect> getDbDialect() {
        return dbDialect;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public static ImmutableDbCoordinates create(Class<? extends Dialect> dialect, String url, String user, String password) {
        ImmutableDbCoordinates settings = new ImmutableDbCoordinates();
        settings.dbDialect = dialect;
        settings.dbUrl = url;
        settings.dbUsername = user;
        settings.dbPassword = password;
        return settings;
    }

}
