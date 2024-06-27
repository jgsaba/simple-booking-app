package hostfully.test.booking_app.domain.persistence.entities;

import jakarta.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @ManyToOne
    Manager manager;

    public Manager getManager() {
        return manager;
    }

    public Property setManager(Manager manager) {
        this.manager = manager;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Property setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }
}
