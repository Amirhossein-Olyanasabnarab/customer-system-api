package dk.dev.app.exception;

public class DuplicatedCustomerException extends RuntimeException {
    public DuplicatedCustomerException(String message) {
        super(message);
    }
    public DuplicatedCustomerException(String message, Throwable cause) {
      super(message, cause);
    }
}
