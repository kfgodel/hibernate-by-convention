package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;

import java.util.Properties;

/**
 * This type represents a hibernate property whose value can be a string
 * Created by kfgodel on 21/03/15.
 */
public class StringProperty extends PropertySupport<String> {

    public StringProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, String value) {
        this.setValueIn(properties, value);
    }

    public static StringProperty create(String propertyName) {
        StringProperty stringProperty = new StringProperty(propertyName);
        return stringProperty;
    }

}
