package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;

import java.util.Properties;

/**
 * This type represents a property whose type is numerical
 * Created by kfgodel on 21/03/15.
 */
public class IntegerProperty extends PropertySupport<Integer> {

    public IntegerProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, Integer value) {
        this.setValueIn(properties, value.toString());
    }

    public static IntegerProperty create(String propertyName) {
        IntegerProperty integerProperty = new IntegerProperty(propertyName);
        return integerProperty;
    }

}
