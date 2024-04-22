package mvc_view.exceptions;

public class InvalidCardNumberException extends Exception {
    public InvalidCardNumberException(String message) {
        super(message);
    }

    public InvalidCardNumberException(){
        super("The card Number is Invalid. It should be 16 digits");
    }
}