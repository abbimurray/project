package mvc_view.exceptions;

public class AccessDeniedException extends Exception {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(){
        super("Aceesss denied at present");
    }
}
