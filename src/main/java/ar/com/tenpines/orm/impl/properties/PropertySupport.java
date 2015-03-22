package ar.com.tenpines.orm.impl.properties;

import java.util.Properties;

/**
 * This class defines the base behavior for represented hibernate properties
 *
 * Created by kfgodel on 21/03/15.
 */
public abstract class PropertySupport<T> {

    private String propertyName;

    public PropertySupport(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value for this property in the given properties object
     * @param properties The properties to configure
     * @param value The value for this property
     */
    public void setValueIn(Properties properties, String value){
        properties.setProperty(this.getPropertyName(), value);
    }

    /**
     * Sets the typed value for this property in the given properties object
     * @param properties The properties to configure
     * @param value The value for this property
     */
    public abstract void setIn(Properties properties, T value);
}
