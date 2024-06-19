package de.schulung.sample.quarkus.shared.interceptors;

import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

@UtilityClass
class AnnotationUtils {
  <A extends Annotation> Optional<A> findAnnotation(Method method, Class<A> annotationClass) {
    Optional<A> result = Optional
      .ofNullable(method.getAnnotation(annotationClass));
    // since Java 9, we could simply use Optional#or(...)
    if (result.isEmpty()) {
      result = findAnnotation(method.getDeclaringClass(), annotationClass);
    }
    return result;
  }

  <A extends Annotation> Optional<A> findAnnotation(Class<?> clazz, Class<A> annotationClass) {
    return Optional.ofNullable(clazz.getAnnotation(annotationClass));
  }

}