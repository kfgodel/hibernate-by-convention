package ar.com.tenpines.orm.api.crud;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.tenpines.orm.api.entities.Identifiable;
import ar.com.tenpines.orm.api.exceptions.CrudException;
import org.hibernate.Session;

import java.util.function.Function;

/**
 * This type represents a database CRUD provider to create, read, update, and delete operations.<br>
 *     This types is an overly simplified interface that should be enough for 80% of the use cases
 *
 * Created by kfgodel on 03/04/15.
 */
public interface CrudProvider {

    /**
     * Saves the given instance state into the database according to current ORM mappings.<br>
     *     The instance is created in the database if no id is present in the object. The state
     *     gets updated if the id is present.
     * @param instance The instance to save or update
     * @return The instance ID (new if it didn't have one)
     */
    Long save(Identifiable instance) throws CrudException;

    /**
     * Deletes a persistent instance from the database according to its ORM mappings.<br>
     * @param instance The instance to delete
     */
    void delete(Identifiable instance);

    /**
     * Executes the given function as a persistence operation, returning the results of the function
     * @param operation The operation to execute
     * @param <T> The expected type of results
     * @return A nary of possible results (from 0, 1, or N)
     */
    <T> Nary<T> perform(Function<Session, Nary<T>> operation);

}
