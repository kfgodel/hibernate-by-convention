package ar.com.tenpines.orm.api.crud;

/**
 * This type represents a persistent database object, that has an database ID to be identified
 *
 * Created by kfgodel on 03/04/15.
 */
public interface Identifiable {

    /**
     * @return The database id, or null if it's not persistent yet
     */
    Long getId();

    /**
     * Sets the id of this object in the databse
     * @param id The given identification
     */
    void setId(Long id);

}
