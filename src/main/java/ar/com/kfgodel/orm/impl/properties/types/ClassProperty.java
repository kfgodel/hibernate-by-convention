package ar.com.kfgodel.orm.impl.properties.types;

import ar.com.kfgodel.orm.impl.properties.PropertySupport;
import org.hibernate.dialect.Dialect;

/**
 * This type represents a hibernate property whose value it's a class reference
 * <p/>
 * Created by kfgodel on 21/03/15.
 */
public class ClassProperty<C> extends PropertySupport<Class<? extends C>> {

  public ClassProperty(String propertyName) {
    super(propertyName);
  }

  @Override
  protected String representAsString(Class<? extends C> value) {
    return value.getName();
  }

  @Override
  protected Class<? extends C> recoverValueFrom(String className) {
    try {
      return (Class<? extends C>) Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("The property value[" + className + "] cannot be loaded as a class", e);
    }
  }

  public static ClassProperty<Dialect> create(String propertyName) {
    ClassProperty<Dialect> property = new ClassProperty<Dialect>(propertyName);
    return property;
  }
}
