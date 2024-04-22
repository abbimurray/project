package mvc_view.exceptions;
public class CardExpiredException extends Exception {
    public CardExpiredException(String message) {
        super(message);
    }

    public CardExpiredException()
    {
        super("The card expiry date is past");
    }
}