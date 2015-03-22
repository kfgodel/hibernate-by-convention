package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;

import java.util.Properties;

/**
 * This type represents a hibernate property expressed in seconds
 * Created by kfgodel on 21/03/15.
 */
public class SecondProperty extends PropertySupport<TimeValue> {
    public SecondProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, TimeValue value) {
        long seconds = value.getUnit().toSeconds(value.getAmount());
        this.setValueIn(properties, String.valueOf(seconds));
    }


    public static SecondProperty create(String propertyName) {
        SecondProperty secondProperty = new SecondProperty(propertyName);
        return secondProperty;
    }

}
