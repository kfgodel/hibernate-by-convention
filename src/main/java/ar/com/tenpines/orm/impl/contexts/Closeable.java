package ar.com.tenpines.orm.impl.contexts;

/**
 * This type represents a resource that needs to be closed after being used
 * Created by kfgodel on 05/03/16.
 */
public interface Closeable {
  /**
   * Frees its resources
   */
  void close();
}
