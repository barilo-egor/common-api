package tgb.cryptoexchange.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
//test
/**
 * Класс для API ответов
 * @param <T> тип данных ответа
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Указывает, был ли запрос успешен.
     */
    private boolean success;

    /**
     * Данные ответа
     */
    private T data;

    /**
     * Ошибка в случае неудачной обработки запроса
     */
    private Error error;

    /**
     * Класс описывающий ошибку при обработке запроса
     */
    @Data
    @Builder
    public static class Error {

        private String message;

        private ErrorCode code;

        /**
         * Перечисление для кодов ошибок
         */
        @AllArgsConstructor
        @Getter
        public enum ErrorCode {
            ENTITY_NOT_FOUND(0);

            private final int code;
        }
    }

    /**
     * Метод для создания успешного ответа
     * @param data данные ответа
     * @return объект успешного ответа с данными
     * @param <T> тип данных
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(data);
        return apiResponse;
    }

    /**
     * Метод для создания ответа с ошибкой
     * @param error объект описывающий ошибку
     * @return объект ответа с ошибкой
     * @param <T> тип данных
     */
    public static <T> ApiResponse<T> error(Error error) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setError(error);
        return apiResponse;
    }
}
