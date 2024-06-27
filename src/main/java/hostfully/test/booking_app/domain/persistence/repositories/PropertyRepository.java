package hostfully.test.booking_app.domain.persistence.repositories;

import hostfully.test.booking_app.domain.persistence.entities.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

}
