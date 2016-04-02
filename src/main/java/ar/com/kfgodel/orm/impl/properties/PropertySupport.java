package ar.com.kfgodel.orm.impl.properties;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.Properties;

/**
 * This class defines the base behavior for represented hibernate properties
 * <p/>
 * Created by kfgodel on 21/03/15.
 */
public abstract class PropertySupport<T> implements HibernateProperty<T> {

  private String propertyName;

  public PropertySupport(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getPropertyName() {
    return propertyName;
  }

  /**
   * Sets the value for this property in the given properties object
   *
   * @param properties The properties to configure
   * @param value      The value for this property
   */
  public void setValueIn(Properties properties, String value) {
    properties.setProperty(this.getPropertyName(), value);
  }

  /**
   * Gets the value for this property as string from the given properties object
   *
   * @param properties The properties to configure
   */
  public Optional<String> getValueFrom(Properties properties) {
    String propertyValue = properties.getProperty(this.getPropertyName());
    return Nary.ofNullable(propertyValue);
  }

  @Override
  public void setIn(Properties properties, T value) {
    String stringRepresentation = representAsString(value);
    this.setValueIn(properties, stringRepresentation);
  }

  @Override
  public Optional<T> getFrom(Properties properties) {
    return getValueFrom(properties)
      .mapOptional(this::recoverValueFrom);
  }

  /**
   * Converts the given value from its own typ to string, in order to
   * store it into the properties object
   *
   * @param value The original value
   * @return a string representation
   */
  protected abstract String representAsString(T value);

  /**
   * Converts the given string representation into its original type
   * @param stringRepresentation The representation of the value
   * @return The restored value
   */
  protected abstract T recoverValueFrom(String stringRepresentation);


}
