package tgb.cryptoexchange.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogResponseBody {
    LogLevel level() default LogLevel.TRACE;

    enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
