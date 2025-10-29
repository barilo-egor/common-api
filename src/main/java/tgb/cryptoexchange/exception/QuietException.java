package tgb.cryptoexchange.exception;

/**
 * Исключение, которое не будет записано в логи.
 */
public class QuietException extends RuntimeException {
    public QuietException() {
    }

    public QuietException(String message) {
        super(message);
    }

    public QuietException(String message, Throwable cause) {
        super(message, cause);
    }
}
