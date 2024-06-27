package hostfully.test.booking_app.domain.persistence.repositories;

import hostfully.test.booking_app.domain.persistence.entities.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Long> {
}
