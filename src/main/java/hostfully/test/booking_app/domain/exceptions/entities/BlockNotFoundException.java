package hostfully.test.booking_app.domain.exceptions.entities;

public class BlockNotFoundException extends EntityNotFoundException {
    public BlockNotFoundException() {
        super("Block entity has not been found");
    }
}
