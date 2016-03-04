package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * This type represents a hibernate property whose value is expressed in milliseconds
 * Created by kfgodel on 21/03/15.
 */
public class MillisecondProperty extends PropertySupport<TimeValue> {
    
    public MillisecondProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    protected String representAsString(TimeValue value) {
        long milliseconds = value.getUnit().toMillis(value.getAmount());
        String stringRepresentation = String.valueOf(milliseconds);
        return stringRepresentation;
    }

    @Override
    protected TimeValue recoverValueFrom(String stringRepresentation) {
        Long millisecondsValue = Long.valueOf(stringRepresentation);
        return TimeValue.create(millisecondsValue, TimeUnit.MILLISECONDS);
    }

    public static MillisecondProperty create(String propertyName) {
        MillisecondProperty millisecondProperty = new MillisecondProperty(propertyName);
        return millisecondProperty;
    }

}
