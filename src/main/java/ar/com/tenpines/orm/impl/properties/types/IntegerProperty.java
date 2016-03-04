package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;

/**
 * This type represents a property whose type is numerical
 * Created by kfgodel on 21/03/15.
 */
public class IntegerProperty extends PropertySupport<Integer> {

    public IntegerProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    protected String representAsString(Integer value) {
        return value.toString();
    }

    @Override
    protected Integer recoverValueFrom(String stringRepresentation) {
        return Integer.valueOf(stringRepresentation);
    }

    public static IntegerProperty create(String propertyName) {
        IntegerProperty integerProperty = new IntegerProperty(propertyName);
        return integerProperty;
    }

}
