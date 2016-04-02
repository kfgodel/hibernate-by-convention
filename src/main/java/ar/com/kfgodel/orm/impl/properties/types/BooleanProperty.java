package ar.com.kfgodel.orm.impl.properties.types;

import ar.com.kfgodel.orm.impl.properties.PropertySupport;

/**
 * This type type represents an hibernate property whose value it's a boolean value
 * <p>
 * Created by kfgodel on 21/03/15.
 */
public class BooleanProperty extends PropertySupport<Boolean> {

  public BooleanProperty(String propertyName) {
    super(propertyName);
  }

  @Override
  protected String representAsString(Boolean value) {
    return value.toString();
  }

  @Override
  protected Boolean recoverValueFrom(String stringRepresentation) {
    return Boolean.valueOf(stringRepresentation);
  }

  public static BooleanProperty create(String propertyName) {
    BooleanProperty booleanProperty = new BooleanProperty(propertyName);
    return booleanProperty;
  }

}
