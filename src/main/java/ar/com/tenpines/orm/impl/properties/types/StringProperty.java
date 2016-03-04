package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;

/**
 * This type represents a hibernate property whose value can be a string
 * Created by kfgodel on 21/03/15.
 */
public class StringProperty extends PropertySupport<String> {

    public StringProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    protected String representAsString(String value) {
        return value;
    }

    @Override
    protected String recoverValueFrom(String stringRepresentation) {
        return stringRepresentation;
    }

    public static StringProperty create(String propertyName) {
        StringProperty stringProperty = new StringProperty(propertyName);
        return stringProperty;
    }

}
