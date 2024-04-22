package mvc_view.exceptions;


public class InvalidDateRangeException extends Exception {
    public InvalidDateRangeException(String message) {
        super(message);
    }

    public InvalidDateRangeException(){
        super("The date is not in the correct format");
    }
}
