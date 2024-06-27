package hostfully.test.booking_app.domain.exceptions.entities;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
