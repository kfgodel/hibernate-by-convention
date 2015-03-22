package ar.com.tenpines.orm.impl.properties.values;

/**
 * This type represents the different possible values for hbm2ddl hibernate property.<br>
 *     http://blog.eyallupu.com/2007/05/hibernates-hbm2ddl-tool.html
 * Created by kfgodel on 21/03/15.
 */
public enum Hbm2DdlValue {
    /**
     * Checks that the db structure is compatible with class mappings
     */
    VALIDATE("validate"),
    /**
     * Updates the DB structure to match class mappings
     */
    UPDATE("update"),
    /**
     * Empties the database before creating it
     */
    CREATE("create"),
    /**
     * Drops  the database after sessionFactory is closed
     */
    CREATE_DROP("create-drop");

    Hbm2DdlValue(String valueName) {
        this.valueName = valueName;
    }

    private String valueName;

    public String getValueName() {
        return valueName;
    }
}
