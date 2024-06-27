package hostfully.test.booking_app.domain.dto.block;

import hostfully.test.booking_app.domain.persistence.entities.Block;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record BlockDTO(

        @FutureOrPresent
        @NotNull
        LocalDate startDate,

        @Future
        @NotNull
        LocalDate endDate,

        @Positive
        @NotNull
        Long propertyId
) {
    public static BlockDTO from(Block block){
        return new BlockDTO(block.getStartDate(), block.getEndDate(), block.getProperty().getId());
    }
}
