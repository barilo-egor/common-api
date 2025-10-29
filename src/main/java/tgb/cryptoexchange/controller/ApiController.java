package tgb.cryptoexchange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tgb.cryptoexchange.exception.QuietException;
import tgb.cryptoexchange.web.ApiResponse;

import java.util.HashMap;
import java.util.Map;

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
        return new ResponseEntity<>(ApiResponse.error(ApiResponse.Error.builder().message(ex.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработка ошибок, не требующих логирования.
     * @param ex исключение не обработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleQuiteException(QuietException ex) {
        return new ResponseEntity<>(ApiResponse.error(ApiResponse.Error.builder().message(ex.getMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
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
        return new ResponseEntity<>(ApiResponse.error(ApiResponse.Error.builder().message(errors.toString()).build()), HttpStatus.BAD_REQUEST);
    }
}
