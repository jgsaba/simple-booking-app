package hostfully.test.booking_app.domain.persistence.repositories;

import hostfully.test.booking_app.domain.persistence.entities.Block;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BlockRepository extends CrudRepository<Block, Long> {

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Block b WHERE b.property.id = :pid AND (" +
            "(:sd < b.startDate  AND :ed > b.startDate) " +
            "OR (:sd > b.startDate AND :sd < b.endDate))")
    boolean checkOverlappingBlocks(@Param("pid") Long pid,
                                   @Param("sd") LocalDate sd,
                                   @Param("ed") LocalDate ed);

}
