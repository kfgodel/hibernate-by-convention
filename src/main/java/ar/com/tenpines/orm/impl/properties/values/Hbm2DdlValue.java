package ar.com.tenpines.orm.impl.properties.values;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.Arrays;

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

  /**
   * Searches the value by the given name,returningan optional that may contain it
   * @param valueName The name that matches the value
   * @return An empty optional if none match
   */
    public static Optional<Hbm2DdlValue> valueByName(String valueName) {
        return Nary.create(Arrays.stream(values())
          .filter((value) -> value.getValueName().equals(valueName))
          .findFirst());
    }
}
