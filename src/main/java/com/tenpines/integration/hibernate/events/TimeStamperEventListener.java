/**
 * 12/11/2012 18:18:55 Copyright (C) 2011 10Pines S.R.L.
 */
package com.tenpines.integration.hibernate.events;

import ar.com.kfgodel.orm.api.entities.Datable;
import org.hibernate.event.spi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Esta clase fecha las entidades del sistema agregandoles fecha de creación y ultima modificación,
 * utilizando los eventos de hibernate.<br>
 * Para utilizara hay que registrarla en el sessionFactory en los eventos que corresponde
 * (pre-insert, pre-update).<br>
 * Ver: http://j4fry.blogspot.com.ar/2009/06/hibernate-event-listeners-with-spring.html <br>
 * Implementación basada en:
 * http://anshuiitk.blogspot.com.ar/2010/11/hibernate-pre-database-opertaion-event.html
 *
 * @author D. García
 */
public class TimeStamperEventListener implements PreInsertEventListener, PreUpdateEventListener {
    private static final long serialVersionUID = 2790781925561450149L;

    private static final Logger LOG = LoggerFactory.getLogger(TimeStamperEventListener.class);

    /**
     * @see org.hibernate.event.spi.PreUpdateEventListener#onPreUpdate(org.hibernate.event.spi.PreUpdateEvent)
     */
    @Override
    public boolean onPreUpdate(final PreUpdateEvent event) {
        final Object entity = event.getEntity();
        if (!Datable.class.isInstance(entity)) {
            // No es una entidad que requiera fecha
            return false;
        }
        final Datable fechable = (Datable) entity;
        actualizarFechaDeModificacion(fechable, event);
        return false;
    }

    /**
     * Establece la fecha de modificación para la entidad pasada
     *
     * @param fechable
     *            La entidad a modificar
     * @param event
     */
    private void actualizarFechaDeModificacion(final Datable fechable, final PreUpdateEvent event) {
        // Modificamos la fecha normalmente
        final LocalDateTime momentoDeActualizacion = LocalDateTime.now();
        fechable.setMomentoDeUltimaModificacion(momentoDeActualizacion);

        // Modificamos el estado que usa hibernate para persistir
        final Object[] state = event.getState();
        setValue(state, Datable.momentoDeUltimaModificacion_FIELD, momentoDeActualizacion, event);
    }

    /**
     * @see org.hibernate.event.spi.PreInsertEventListener#onPreInsert(org.hibernate.event.spi.PreInsertEvent)
     */
    @Override
    public boolean onPreInsert(final PreInsertEvent event) {
        final Object entity = event.getEntity();
        if (!Datable.class.isInstance(entity)) {
            // No es una entidad que requiera fecha
            return false;
        }
        final Datable fechable = (Datable) entity;
        actualizarFechaDeCreacion(fechable, event);
        return false;
    }

    /**
     * Establece la fecha de creación y la de ultima modificacion al mismo valor
     *
     * @param fechable
     *            La entidad a modificar
     * @param event
     */
    private void actualizarFechaDeCreacion(final Datable fechable, final PreInsertEvent event) {
        LocalDateTime momentoDeCreacion = fechable.getMomentoDeCreacion();
        // Verificamos si por alguna razon ya tiene definida una fecha
        if (momentoDeCreacion == null) {
            momentoDeCreacion = LocalDateTime.now();
            fechable.setMomentoDeCreacion(momentoDeCreacion);
        }

        // Seteamos la fecha normalmente
        fechable.setMomentoDeUltimaModificacion(momentoDeCreacion);

        // La seteamos en el estado que usa hibernate para persistir
        final Object[] state = event.getState();
        setValue(state, Datable.momentoDeCreacion_FIELD, momentoDeCreacion, event);
        setValue(state, Datable.momentoDeUltimaModificacion_FIELD, momentoDeCreacion, event);
    }

    /**
     * Setea el valor de un atributo en el etado a persistir
     */
    void setValue(final Object[] state, final String propertyToSet, final Object value,
                  final AbstractPreDatabaseOperationEvent event) {
        final String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();
        final int index = this.indexOf(propertyNames, propertyToSet);
        if (index >= 0) {
            state[index] = value;
        } else {
            LOG.error(
                    "No se pudo setear automaticamente el atributo [{}] en la entidad[{}] porque no se encontro por nombre en su clase[{}]",
                    propertyToSet, event.getEntity(), event.getEntity().getClass().getName());
        }
    }

    private int indexOf(String[] propertyNames, String propertyToSet) {
        for (int i = 0; i < propertyNames.length; i++) {
            String propertyName = propertyNames[i];
            if(Objects.equals(propertyName, propertyToSet)){
                return i;
            }
        }
        return -1;
    }

    public static TimeStamperEventListener create() {
        TimeStamperEventListener eventListener = new TimeStamperEventListener();
        return eventListener;
    }

}
