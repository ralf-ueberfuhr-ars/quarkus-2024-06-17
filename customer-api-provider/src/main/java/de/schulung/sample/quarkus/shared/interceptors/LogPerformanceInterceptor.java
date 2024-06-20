package de.schulung.sample.quarkus.shared.interceptors;

import io.quarkus.arc.log.LoggerName;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@LogPerformance
@Priority(10)
public class LogPerformanceInterceptor {

  @LoggerName("performance")
  Logger log;

  private Logger.Level findLevel(InvocationContext ic) {
    return AnnotationUtils
      .findAnnotation(ic.getMethod(), LogPerformance.class)
      .map(LogPerformance::value)
      .orElse(Logger.Level.INFO);
  }

  @AroundInvoke
  public Object logPerformance(InvocationContext ic) throws Exception {
    var methodName = ic.getMethod().getName();
    var level = findLevel(ic);
    var ts1 = System.currentTimeMillis();
    try {
      return ic.proceed();
    } finally {
      var ts2 = System.currentTimeMillis();
      log.log(level, "Methode " + methodName + ": " + (ts2 - ts1) + "ms");
    }
  }

}
