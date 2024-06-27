package hostfully.test.booking_app.domain.persistence.entities;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    public Long getId() {
        return id;
    }

    public Manager setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Manager setName(String name) {
        this.name = name;
        return this;
    }
}
