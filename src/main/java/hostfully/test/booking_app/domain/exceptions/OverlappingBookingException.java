package hostfully.test.booking_app.domain.exceptions;

public class OverlappingBookingException extends RuntimeException{
    public OverlappingBookingException() {
        super("It was not possible to register the booking due to overlapping dates");
    }
}
