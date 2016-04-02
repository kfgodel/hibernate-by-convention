/**
 * 12/11/2012 18:22:19 Copyright (C) 2011 10Pines S.R.L.
 */
package ar.com.kfgodel.orm.api.entities;

import java.time.LocalDateTime;

/**
 * Esta interfaz es aplicable a las entidades que tienen fecha de creación y de ultima modificación
 *
 * @author D. García
 */
public interface Datable {

  /**
   * Establece el momento que se define como creación de esta entidad
   *
   * @param momento El momento de creación nuevo
   */
  void setMomentoDeCreacion(LocalDateTime momento);

  /**
   * Devuelve el momento que se toma de referencia como creación de esta instancia
   *
   * @return El momento que se registro en la creación de la instancia (normalmente al insertarlo
   * en la base)
   */
  LocalDateTime getMomentoDeCreacion();

  /**
   * Nombre del atributo referenciable por reflection brindado por getMomentoDeCreacion()
   */
  String momentoDeCreacion_FIELD = "momentoDeCreacion";

  /**
   * Establece el momento que se tomará como referencia de la última modificación que tuvo esta
   * instancia en la base
   *
   * @param momento El momento que se tomará como ultima modificación
   */
  void setMomentoDeUltimaModificacion(LocalDateTime momento);

  /**
   * Devuelve el momento en el cual se modifico esta instancia por ultima vez
   *
   * @return (el momento que se le registro al hacer un update)
   */
  LocalDateTime getMomentoDeUltimaModificacion();

  /**
   * Nombre del atributo referenciable por reflection brindado por getMomentoDeUltimaModificacion()
   */
  String momentoDeUltimaModificacion_FIELD = "momentoDeUltimaModificacion";

}
