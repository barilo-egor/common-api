package tgb.cryptoexchange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tgb.cryptoexchange.exception.BadRequestException;
import tgb.cryptoexchange.exception.QuietException;
import tgb.cryptoexchange.exception.ServiceUnavailableException;
import tgb.cryptoexchange.web.ApiResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Базовый контроллер для REST API
 */
@Slf4j
public abstract class ApiController {

    /**
     * Обработка ошибок
     * @param ex исключение не обработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex){
        log.error("Необработанная ошибка: ", ex);
        return new ResponseEntity<>(ApiResponse.error("Internal server error. Contact support."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка ошибок, не требующих логирования.
     * @param ex исключение не обработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleQuiteException(QuietException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка ошибок с возвращением 400 статуса.
     * @param ex исключение не обработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Обработка ошибок с возвращением 503 статуса.
     * @param ex исключение не обработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleServiceUnavailableException(ServiceUnavailableException ex) {
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Обработка ошибки валидации {@link jakarta.validation.Valid}
     * @param ex исключение валидации
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        errors.append("Fields errors: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append("field ").append(error.getField()).append(" ").append(error.getDefaultMessage()).append(", ")
        );
        errors.deleteCharAt(errors.length() - 1);
        errors.deleteCharAt(errors.length() - 1);
        return new ResponseEntity<>(ApiResponse.error(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handle(MethodArgumentTypeMismatchException ex) {
        String name = ex.getName();
        Class<?> requiredType = ex.getRequiredType();
        String type = (requiredType != null) ? requiredType.getSimpleName() : "unknown";
        Object valueObj = ex.getValue();
        String value = (valueObj != null) ? valueObj.toString() : "null";
        String message = String.format(
                "Parameter '%s' should be of type '%s', but value '%s' is invalid",
                name, type, value
        );
        return new ResponseEntity<>(ApiResponse.error(message), HttpStatus.BAD_REQUEST);
    }
}
