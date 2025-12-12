package tgb.cryptoexchange.exception;

/**
 * Пробрасывается для возвращения 400 http статуса.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
