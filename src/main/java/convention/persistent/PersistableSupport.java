/**
 * 11/03/2011 14:27:00 Copyright (C) 2006 Darío L. García
 *
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package convention.persistent;

import ar.com.kfgodel.orm.api.entities.Datable;
import ar.com.kfgodel.orm.api.entities.Persistable;
import com.tenpines.commons.components.lang.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Superclase para las entidades persistentes que implementa su ID con un Long.<br>
 * Se redefine el equals y el hashcode para utilizar el ID como referencia para la igualdad
 *
 * @author D. García
 */
@MappedSuperclass
public class PersistableSupport implements Persistable, Datable {
    private static final Logger LOG = LoggerFactory.getLogger(PersistableSupport.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime momentoDeCreacion;

    private LocalDateTime momentoDeUltimaModificacion;

    /**
     * Este campo permite detectar modificaciones concurrentes sobre las entidades en forma
     * optimista. Si falla el lockeo, la última transacción en cerrar tendrá una
     * excepción por lockeo optimista, indicando que otra transacción le
     * modificó los datos
     */
    @Version
    private Long persistenceVersion;
    public static final String persistenceVersion_FIELD = "persistenceVersion";


    public Long getPersistenceVersion() {
        return persistenceVersion;
    }

    public void setPersistenceVersion(final Long persistenceVersion) {
        this.persistenceVersion = persistenceVersion;
    }

    /**
     * @see Persistable#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Compara esta entidad con otra considerandola por ID.<br>
     * Serán consideradas iguales sólo si son la misma instancia o si siendo del mismo tipo tienen
     * el mismo ID.<br>
     * Si alguna de las entidades no tiene ID, entonces serán consideradas entidades distintas. (al
     * persistirlas cada una tendrá su ID distinto)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            // Cortamos por el caso más fácil y común
            return true;
        }
        if (obj == null) {
            // Nunca somos iguales a un null
            return false;
        }
        final Long thisId = this.getId();
        if (thisId == null) {
            return false;
        }
        if (!this.getClass().isAssignableFrom(obj.getClass()) && !obj.getClass().isAssignableFrom(this.getClass())){
            // Si no es del mismo tipo asumimos que son tablas distintas y por lo tanto objetos
            // distintos, el id podría coincidir pero son distintas entidades
            LOG.debug("Considerando como distintos a [{}] y [{}] por ser de clases distintas", this, obj);
            return false;
        }
        if(!Persistable.class.isInstance(obj)){
            return false;
        }
        final Persistable other = (Persistable) obj;
        // Finalmente son considerados iguales si tienen el mismo ID y son del mismo tipo
        return thisId.equals(other.getId());
    }

    /**
     * Implementación del hashcode que usa el ID como hashcode de referencia.<br>
     * Esta implementación usa el hashcode tradicional si esta entidad no tiene ID, con lo que hay
     * que ser cuidadosos al agregar entidades sin persistir en colecciones que usen el hashcode.
     * Por ejemplo un HashSet, que puede considerar como distintos a la msima entidad antes y
     * después de persistida
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.getId() != null) {
            return this.getId().hashCode();
        } else {
            LOG.debug("Usando Object.hashCode() para un persistible por que no tiene ID");
            return super.hashCode();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToString.de(this).toString();
    }

    /**
     * @see Datable#getMomentoDeCreacion()
     */
    @Override
    public LocalDateTime getMomentoDeCreacion() {
        return momentoDeCreacion;
    }

    /**
     * @see Datable#setMomentoDeCreacion(LocalDateTime)
     */
    @Override
    public void setMomentoDeCreacion(final LocalDateTime momentoDeCreacion) {
        this.momentoDeCreacion = momentoDeCreacion;
    }

    /**
     * @see Datable#getMomentoDeUltimaModificacion()
     */
    @Override
    public LocalDateTime getMomentoDeUltimaModificacion() {
        return momentoDeUltimaModificacion;
    }

    /**
     * @see Datable#setMomentoDeCreacion(LocalDateTime)
     */
    @Override
    public void setMomentoDeUltimaModificacion(final LocalDateTime momentoDeUltimaActualizacion) {
        this.momentoDeUltimaModificacion = momentoDeUltimaActualizacion;
    }

}
