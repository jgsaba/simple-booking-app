package hostfully.test.booking_app.domain.exceptions.entities;

public class PropertyNotFoundException extends EntityNotFoundException {
    public PropertyNotFoundException() {
        super("Property entity has not been found");
    }
}
