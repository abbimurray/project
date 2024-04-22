package mvc_view.exceptions;
public class InvalidSecurityCodeException extends Exception {
    public InvalidSecurityCodeException(String message) {
        super(message);
    }
    public InvalidSecurityCodeException(){
        super("The Security code must be 3 to 4 digits only");
    }
}