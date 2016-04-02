package ar.com.kfgodel.orm.api.operations;

import ar.com.kfgodel.orm.api.SessionContext;

/**
 * This type represents an operation that needs an hibernate session context to be executed.<br>
 * It represents a unit of work in the context of a persistent session (access to persistent objects).<br>
 * <br>
 * A session operation can involve multiple transaction operations, and its only guarantee is that it will
 * have a hibernate session available.
 * Created by kfgodel on 05/04/15.
 */
public interface SessionOperation<R> {

  /**
   * Executes this operation with the given session context where a session is available
   *
   * @param sessionContext The context from which to fetch persistent objects
   * @return The operation result
   */
  R applyWithSessionOn(SessionContext sessionContext);
}
