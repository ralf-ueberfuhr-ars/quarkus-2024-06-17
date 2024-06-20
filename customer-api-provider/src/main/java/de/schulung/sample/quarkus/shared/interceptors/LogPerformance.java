package de.schulung.sample.quarkus.shared.interceptors;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@InterceptorBinding
public @interface LogPerformance {
}
