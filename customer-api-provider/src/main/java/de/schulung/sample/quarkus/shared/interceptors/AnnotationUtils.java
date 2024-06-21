package de.schulung.sample.quarkus.shared.interceptors;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

@UtilityClass
class AnnotationUtils {
  <A extends Annotation> Optional<A> findAnnotation(Method method, Class<A> annotationClass) {
    return Optional
      .ofNullable(method.getAnnotation(annotationClass))
      .or(() -> findAnnotation(method.getDeclaringClass(), annotationClass));
  }

  <A extends Annotation> Optional<A> findAnnotation(Class<?> clazz, Class<A> annotationClass) {
    return Optional
      .ofNullable(clazz.getAnnotation(annotationClass));
  }

}