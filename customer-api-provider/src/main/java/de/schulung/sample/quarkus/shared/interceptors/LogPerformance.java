package de.schulung.sample.quarkus.shared.interceptors;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import org.jboss.logging.Logger;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@InterceptorBinding
public @interface LogPerformance {

  @Nonbinding
  Logger.Level value() default Logger.Level.INFO;

}
