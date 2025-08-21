package tgb.cryptoexchange.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, с помощью которой можно логировать ответ метода в формате json.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogResponseBody {
    /**
     * Уровень, на котором требуется логирование
     * @return уровень логирования, по умолчанию TRACE
     */
    LogLevel level() default LogLevel.TRACE;

    enum LogLevel {
        TRACE, DEBUG, INFO, WARN, ERROR
    }
}
