package mvc_view.exceptions;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(){
        super("The input is not valid");
    }
}