package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import ar.com.tenpines.orm.impl.properties.values.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * This type represents a hibernate property expressed in seconds
 * Created by kfgodel on 21/03/15.
 */
public class SecondProperty extends PropertySupport<TimeValue> {
    public SecondProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    protected String representAsString(TimeValue value) {
        long seconds = value.getUnit().toSeconds(value.getAmount());
        String stringRepresentation = String.valueOf(seconds);
        return stringRepresentation;
    }

    @Override
    protected TimeValue recoverValueFrom(String stringRepresentation) {
        Long valueInseconds = Long.valueOf(stringRepresentation);
        return TimeValue.create(valueInseconds, TimeUnit.SECONDS);
    }

    public static SecondProperty create(String propertyName) {
        SecondProperty secondProperty = new SecondProperty(propertyName);
        return secondProperty;
    }

}
