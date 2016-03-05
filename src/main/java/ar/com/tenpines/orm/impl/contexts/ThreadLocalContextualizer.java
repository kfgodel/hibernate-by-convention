package ar.com.tenpines.orm.impl.contexts;

import ar.com.kfgodel.nary.impl.NaryFromNative;
import ar.com.kfgodel.optionals.Optional;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation that uses a thread local to keep track of the current session context
 * Created by kfgodel on 05/03/16.
 */
public class ThreadLocalContextualizer<C extends Closeable> implements SessionContextualizer<C> {

  private ThreadLocal<C> currentThreadContext;
  private Supplier<C> contextCreator;

  public static<C extends Closeable> ThreadLocalContextualizer<C> create(Supplier<C> sessionCreator, ThreadLocal<C> threadVariable) {
    ThreadLocalContextualizer contextualizer = new ThreadLocalContextualizer();
    contextualizer.contextCreator = sessionCreator;
    contextualizer.currentThreadContext = threadVariable;
    return contextualizer;
  }

  @Override
  public <R> R ensureContextFor(Function<C, R> operation) {
    Optional<C> existingContext = getExistingContext();
    return existingContext
      .mapOptional(operation::apply)
      .orElseGet(()-> resultFromNewContextAnd(operation));
  }

  /**
   * Executes the given opertion with a newly created context stored in the thread local
   * @param operation The operation to execute
   * @param <R> The expected result type
   * @return The result of the operation
   */
  private <R> R resultFromNewContextAnd(Function<C,R> operation) {
    C newContext = createNewContext();
    try {
      return operation.apply(newContext);
    } finally {
      closeContext(newContext);
    }
  }

  /**
   * Closes the contexts and removes it from current thread, making it unavailable
   * for future operations
   * @param newContext The context to dispose
   */
  private void closeContext(C newContext) {
    currentThreadContext.remove();
    newContext.close();
  }

  /**
   * Creates a new session context in the current thread, making it available to
   * nested operations a a thread local
   * @return The created context
   */
  private C createNewContext() {
    C newContext = contextCreator.get();
    currentThreadContext.set(newContext);
    return newContext;
  }

  /**
   * Fetches the context that is available in he current thread as a local.
   * An empty optional is returned if none is available
   * @return The previously created context or empty
   */
  private Optional<C> getExistingContext() {
    C existingContext = (C) currentThreadContext.get();
    return NaryFromNative.ofNullable(existingContext);
  }
}
