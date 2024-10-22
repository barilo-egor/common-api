package tgb.cryptoexchange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tgb.cryptoexchange.web.ApiResponse;

/**
 * Базовый контроллер для REST API
 */
@Slf4j
public abstract class ApiController {

    /**
     * Обработка ошибок
     * @param ex исключение не ообработанное на контроллере
     * @return ответ с ошибкой
     */
    @ExceptionHandler
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){
        log.debug("Необработнная ошибка: ", ex);
        return new ResponseEntity<>(ApiResponse.error(ApiResponse.Error.builder().message(ex.getMessage()).build()), HttpStatus.OK);
    }
}
