package tgb.cryptoexchange.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ResponseLoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(logResponseBody)")
    public Object logMethodResponse(ProceedingJoinPoint joinPoint, LogResponseBody logResponseBody) throws Throwable {
        return log(joinPoint, logResponseBody.level());
    }

    @Around("@within(logResponseBody)")
    public Object logClassResponse(ProceedingJoinPoint joinPoint, LogResponseBody logResponseBody) throws Throwable {
        return log(joinPoint, logResponseBody.level());
    }

    private Object log(ProceedingJoinPoint joinPoint, LogResponseBody.LogLevel level) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();

        Object result = joinPoint.proceed();

        switch (level) {
        case TRACE -> log.trace("Ответ метода {}: {}", methodName, objectMapper.writeValueAsString(result));
        case DEBUG -> log.debug("Ответ метода {}: {}", methodName, objectMapper.writeValueAsString(result));
        case INFO -> log.info("Ответ метода {}: {}", methodName, objectMapper.writeValueAsString(result));
        case WARN -> log.warn("Ответ метода {}: {}", methodName, objectMapper.writeValueAsString(result));
        case ERROR -> log.error("Ответ метода {}: {}", methodName, objectMapper.writeValueAsString(result));
        }

        return result;
    }
}
