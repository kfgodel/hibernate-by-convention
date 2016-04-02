package ar.com.tenpines.orm.api.entities;

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
     * Nombre del atributo disponible por getId() referenciable por código reflexivo
     */
    String id_FIELD = "id";


    /**
     * Sets the id of this object in the databse
     * @param id The given identification
     */
    void setId(Long id);

}
