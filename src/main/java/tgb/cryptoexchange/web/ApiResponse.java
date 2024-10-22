package tgb.cryptoexchange.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;

    private T data;

    private Error error;

    @Data
    public static class Error {

        private String message;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(true);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(Error error) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setSuccess(false);
        apiResponse.setError(error);
        return apiResponse;
    }
}
