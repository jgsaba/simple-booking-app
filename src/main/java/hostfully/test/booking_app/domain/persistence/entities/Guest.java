package hostfully.test.booking_app.domain.persistence.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "guest")
    List<Booking> bookings;

    public Long getId() {
        return id;
    }

    public Guest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Guest setName(String name) {
        this.name = name;
        return this;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Guest setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        return this;
    }
}
