package tgb.cryptoexchange.exception;

public class EmptyResponseBodyException extends RuntimeException {

    public EmptyResponseBodyException() {
    }

    public EmptyResponseBodyException(String message) {
        super(message);
    }

    public EmptyResponseBodyException(String message, Throwable cause) {
        super(message, cause);
    }
}
