package mvc_view.exceptions;

public class ReservationDeletionException extends Exception {
    public ReservationDeletionException(String message) {
        super(message);
    }

    public ReservationDeletionException(){
        super("The reservation cannot be deleted if the date/ time is in the past");
    }
}
