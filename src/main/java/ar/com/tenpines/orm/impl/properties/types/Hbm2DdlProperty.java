package ar.com.tenpines.orm.impl.properties.types;

import ar.com.tenpines.orm.impl.properties.PropertySupport;
import ar.com.tenpines.orm.impl.properties.values.Hbm2DdlValue;

/**
 * This type represents the hibernate property that defines the ddl strategy
 * <p/>
 * Created by kfgodel on 21/03/15.
 */
public class Hbm2DdlProperty extends PropertySupport<Hbm2DdlValue> {

  public Hbm2DdlProperty(String propertyName) {
    super(propertyName);
  }

  @Override
  protected String representAsString(Hbm2DdlValue value) {
    return value.getValueName();
  }

  @Override
  protected Hbm2DdlValue recoverValueFrom(String valueName) {
    return Hbm2DdlValue.valueByName(valueName)
      .orElseThrow(() -> new IllegalArgumentException("There's no value for the given name: " + valueName));
  }

  public static Hbm2DdlProperty create() {
    Hbm2DdlProperty hbm2DdlProperty = new Hbm2DdlProperty("hibernate.hbm2ddl.auto");
    return hbm2DdlProperty;
  }

}
