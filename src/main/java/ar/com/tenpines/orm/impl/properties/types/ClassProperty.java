package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import org.hibernate.dialect.Dialect;

import java.util.Properties;

/**
 * This type represents a hibernate property whose value it's a class reference
 *
 * Created by kfgodel on 21/03/15.
 */
public class ClassProperty<C> extends PropertySupport<Class<? extends C>> {

    public ClassProperty(String propertyName) {
        super(propertyName);
    }

    /**
     * Sets the value of this property using the actual class instead of its name
     * @param properties The properties object to configure
     * @param dialectType The dialect to use
     */
    @Override
    public void setIn(Properties properties, Class<? extends C> dialectType) {
        this.setValueIn(properties, dialectType.getName());
    }


    public static ClassProperty<Dialect> create(String propertyName) {
        ClassProperty<Dialect> property = new ClassProperty<Dialect>(propertyName);
        return property;
    }
}
