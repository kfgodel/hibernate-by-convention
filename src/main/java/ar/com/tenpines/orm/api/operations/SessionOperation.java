package ar.com.tenpines.orm.api.operations;

import ar.com.tenpines.orm.api.SessionContext;

/**
 * This type represents an operation done with an hibernate session context.<br>
 *     It represents a unit of work in the context of a persistent session (access to persistent objects)
 *
 * Created by kfgodel on 05/04/15.
 */
public interface SessionOperation<R> {

    /**
     * Executes this operation with the given context, returning the result
     * @param sessionContext The context from wich to fetch persistent objects
     * @return The operation result
     */
    R applyWith(SessionContext sessionContext);
}
