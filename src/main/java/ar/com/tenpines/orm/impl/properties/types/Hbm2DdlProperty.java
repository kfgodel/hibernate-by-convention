package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.values.Hbm2DdlValue;
import ar.com.tenpines.orm.impl.properties.PropertySupport;

import java.util.Properties;

/**
 * This type represents the hibernate property that defines the ddl strategy
 *
 * Created by kfgodel on 21/03/15.
 */
public class Hbm2DdlProperty extends PropertySupport<Hbm2DdlValue> {

    public Hbm2DdlProperty(String propertyName) {
        super(propertyName);
    }

    @Override
    public void setIn(Properties properties, Hbm2DdlValue value) {
        this.setValueIn(properties, value.getValueName());
    }

    public static Hbm2DdlProperty create() {
        Hbm2DdlProperty hbm2DdlProperty = new Hbm2DdlProperty("hibernate.hbm2ddl.auto");
        return hbm2DdlProperty;
    }

}
