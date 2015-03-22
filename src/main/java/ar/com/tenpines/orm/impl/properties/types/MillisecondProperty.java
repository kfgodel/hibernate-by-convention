package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;

import java.util.Properties;

/**
 * This type represents a hibernate property whose value is expressed in milliseconds
 * Created by kfgodel on 21/03/15.
 */
public class MillisecondProperty extends PropertySupport<TimeValue> {
    
    public MillisecondProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, TimeValue value) {
        long milliseconds = value.getUnit().toMillis(value.getAmount());
        this.setValueIn(properties, String.valueOf(milliseconds));
    }

    public static MillisecondProperty create(String propertyName) {
        MillisecondProperty millisecondProperty = new MillisecondProperty(propertyName);
        return millisecondProperty;
    }

}
