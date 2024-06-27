package hostfully.test.booking_app.config;

import hostfully.test.booking_app.domain.persistence.entities.Guest;
import hostfully.test.booking_app.domain.persistence.entities.Manager;
import hostfully.test.booking_app.domain.persistence.entities.Property;
import hostfully.test.booking_app.domain.persistence.repositories.GuestRepository;
import hostfully.test.booking_app.domain.persistence.repositories.ManagerRepository;
import hostfully.test.booking_app.domain.persistence.repositories.PropertyRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader {

    private final PropertyRepository propertyRepository;
    private final GuestRepository guestRepository;
    private final ManagerRepository managerRepository;


    public DataLoader(PropertyRepository propertyRepository, GuestRepository guestRepository, ManagerRepository managerRepository) {
        this.propertyRepository = propertyRepository;
        this.guestRepository = guestRepository;
        this.managerRepository = managerRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData(){

        var man1 = new Manager().setName("Bruce Banner");
        var man2 = new Manager().setName("Reed Richards");

        var guest1 = new Guest().setName("John Stewart");
        var guest2 = new Guest().setName("Diana Prince");

        managerRepository.saveAll(Arrays.asList(man1, man2));
        guestRepository.saveAll(Arrays.asList(guest1, guest2));

        var prop1 = new Property().setName("Windtown kitehouse").setManager(new Manager().setId(1L));
        var prop2 = new Property().setName("Hotel California South").setManager(new Manager().setId(2L));

        propertyRepository.save(prop1);
        propertyRepository.save(prop2);

    }
}
