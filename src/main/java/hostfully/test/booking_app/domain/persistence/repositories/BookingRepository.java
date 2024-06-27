package hostfully.test.booking_app.domain.persistence.repositories;

import hostfully.test.booking_app.domain.persistence.entities.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b WHERE b.property.id = :pid AND (b.status = 'OPEN' OR b.status = 'CHECKED_IN') AND (" +
            "(:sd = b.checkinDate) OR " +
            "(:sd < b.checkinDate  AND :ed > b.checkinDate) OR " +
            "(:sd > b.checkinDate AND :sd < b.checkoutDate))")
    boolean checkOverlappingBookings(@Param("pid") Long pid,
                                     @Param("sd") LocalDate sd,
                                     @Param("ed") LocalDate ed);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b WHERE b.id != :bid AND b.property.id = :pid AND (b.status = 'OPEN' OR b.status = 'CHECKED_IN') AND (" +
            "(:sd = b.checkinDate) OR " +
            "(:sd < b.checkinDate  AND :ed > b.checkinDate) OR " +
            "(:sd > b.checkinDate AND :sd < b.checkoutDate))")
    boolean checkOverlappingBookingsForUpdate(@Param("bid") Long bid,
                                              @Param("pid") Long pid,
                                              @Param("sd") LocalDate sd,
                                              @Param("ed") LocalDate ed);

    @Query("SELECT b FROM Booking b WHERE b.property.id = :pid AND (b.status = 'OPEN' OR b.status = 'CHECKED_IN') AND (" +
            "(:sd = b.checkinDate) OR " +
            "(:sd < b.checkinDate  AND :ed > b.checkinDate) OR " +
            "(:sd > b.checkinDate AND :sd < b.checkoutDate))")
    List<Booking> findOverlappingBookings(@Param("pid") Long pid,
                                          @Param("sd") LocalDate sd,
                                          @Param("ed") LocalDate ed);
}
