package hostfully.test.booking_app.domain.exceptions.entities;

public class BookingNotFoundException extends EntityNotFoundException {
    public BookingNotFoundException() {
        super("Booking entity has not been found");
    }
}
