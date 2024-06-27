package hostfully.test.booking_app.domain.dto.booking;

import hostfully.test.booking_app.domain.persistence.entities.Booking;
import java.time.LocalDate;

public record BookingAckDTO (

        Long id,
        LocalDate checkinDate,
        LocalDate checkoutDate,
        String guest,
        String property,
        String manager,
        String status
) {
    public static BookingAckDTO from(Booking booking){
        return new BookingAckDTO(booking.getId(),
                booking.getCheckinDate(),
                booking.getCheckoutDate(),
                booking.getGuest().getName(),
                booking.getProperty().getName(),
                booking.getProperty().getManager().getName(),
                booking.getStatus().toString());
    }
}
