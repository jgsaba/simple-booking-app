package hostfully.test.booking_app.domain.dto.booking;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UpdateBookingDTO(
        @FutureOrPresent
        @NotNull
        LocalDate checkinDate,

        @Future
        @NotNull
        LocalDate checkoutDate,

        @NotBlank
        @NotNull
        String guestName
) {}
