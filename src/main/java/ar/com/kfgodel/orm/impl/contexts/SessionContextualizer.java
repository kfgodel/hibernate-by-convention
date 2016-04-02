package ar.com.kfgodel.orm.impl.contexts;

import java.util.function.Function;

/**
 * This type knows how to create and re-use session contexts for operations
 * <p>
 * Created by kfgodel on 05/03/16.
 */
public interface SessionContextualizer<C extends Closeable> {
  /**
   * Executes the given operation under a context, providing a new one if
   * there's no previous, or re-using the existing if there's one.<br>
   * The provided context is passed as an argument to the given function
   *
   * @param operation The operation to execute
   * @param <R>       The expected result type
   * @return The operation result
   */
  <R> R ensureContextFor(Function<C, R> operation);
}
