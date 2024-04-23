package mvc_view.exceptions;
public class SessionExpiredException extends Exception {
    public SessionExpiredException(String message) {
        super(message);
    }

    public SessionExpiredException(){
        super("The session has expired");
    }
}