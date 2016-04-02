package ar.com.kfgodel.orm.impl.scanner;

import org.reflections.Reflections;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This type represents a scanner of classpath to find persistent types
 * Created by kfgodel on 22/03/15.
 */
public class EntityClasspathScanner {

  private String rootPackage;

  public static EntityClasspathScanner create(String basePackageName) {
    EntityClasspathScanner scanner = new EntityClasspathScanner();
    scanner.rootPackage = basePackageName;
    return scanner;
  }

  /**
   * Looks under the base package name recursively searching for types annotated with
   * persistence annotations
   *
   * @return The set of persistent types found
   */
  public Set<Class<?>> findPersistentTypes() {
    Set<Class<?>> persistentTypes = new LinkedHashSet<>();

    Class<? extends Annotation>[] annotationTypes = new Class[]{Embeddable.class, MappedSuperclass.class, Entity.class};

    Reflections reflections = new Reflections(rootPackage);
    for (Class<? extends Annotation> annotationType : annotationTypes) {
      Set<Class<?>> annotatedTypes = reflections.getTypesAnnotatedWith(annotationType);
      persistentTypes.addAll(annotatedTypes);
    }

    return persistentTypes;
  }
}
