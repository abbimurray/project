package mvc_view.exceptions;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String message) {
        super(message);

    }

    public ResourceNotFoundException(){
        super("The resource can not be found. Please try again.");
    }
}