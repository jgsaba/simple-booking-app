package hostfully.test.booking_app.domain.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record BookingDTO(
        @FutureOrPresent
        @NotNull
        LocalDate checkinDate,

        @Future
        @NotNull
        LocalDate checkoutDate,

        @Positive
        @NotNull
        Long guestId,

        @Positive
        @NotNull
        Long propertyId
) {}
