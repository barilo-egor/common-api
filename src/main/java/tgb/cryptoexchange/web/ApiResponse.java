package tgb.cryptoexchange.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {

        private String message;

        @Schema(
                implementation = Integer.class,
                description = "Код ошибки",
                example = "1"
        )
        @JsonSerialize(using = ErrorCode.Serializer.class)
        private ErrorCode code;

        /**
         * Перечисление для кодов ошибок
         */
        @AllArgsConstructor
        @Getter
        public enum ErrorCode {
            ENTITY_NOT_FOUND(0);

            private final int code;

            public static class Serializer extends JsonSerializer<Error> {

                @Override
                public void serialize(Error error, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                        throws IOException {
                    jsonGenerator.writeString(String.valueOf(error.getCode().getCode()));
                }

            }
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
