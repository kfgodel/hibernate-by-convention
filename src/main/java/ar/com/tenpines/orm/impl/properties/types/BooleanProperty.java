package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;

import java.util.Properties;

/**
 * This type type represents an hibernate property whose value it's a boolean value
 *
 * Created by kfgodel on 21/03/15.
 */
public class BooleanProperty extends PropertySupport<Boolean> {

    public BooleanProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, Boolean value) {
        this.setValueIn(properties, value.toString());
    }

    public static BooleanProperty create(String propertyName) {
        BooleanProperty booleanProperty = new BooleanProperty(propertyName);
        return booleanProperty;
    }

}
