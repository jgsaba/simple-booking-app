package hostfully.test.booking_app.domain.exceptions.entities;

public class GuestNotFoundException extends EntityNotFoundException {
    public GuestNotFoundException() {
        super("Guest entity has not been found");
    }
}
