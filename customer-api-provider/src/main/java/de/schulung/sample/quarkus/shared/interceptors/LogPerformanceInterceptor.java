package de.schulung.sample.quarkus.shared.interceptors;

import io.quarkus.arc.log.LoggerName;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@LogPerformance
public class LogPerformanceInterceptor {

  @LoggerName("perf")
  Logger log;

  @AroundInvoke
  public Object logPerformance(InvocationContext ic) throws Exception {
    var methodName = ic.getMethod().getName();
    var ts1 = System.currentTimeMillis();
    try {
      return ic.proceed();
    } finally {
      var ts2 = System.currentTimeMillis();
      log.info("Methode " + methodName + ": " + (ts2-ts1) + "ms");
    }
  }

}
