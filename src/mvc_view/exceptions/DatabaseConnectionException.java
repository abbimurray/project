package mvc_view.exceptions;

public class DatabaseConnectionException extends Exception {
    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}