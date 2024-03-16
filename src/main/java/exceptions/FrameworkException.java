package exceptions;

public class FrameworkException extends RuntimeException {

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
    }
}
