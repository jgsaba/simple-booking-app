package hostfully.test.booking_app.domain.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(columnDefinition = "DATE")
    LocalDate checkinDate;
    @Column(columnDefinition = "DATE")
    LocalDate checkoutDate;
    @ManyToOne
    Property property;
    @ManyToOne(cascade = CascadeType.MERGE)
    Guest guest;

    @Enumerated(EnumType.STRING)
    BookingStatus status;

    public Booking(){}

    public Booking(LocalDate checkinDate, LocalDate checkoutDate, Property property, Guest guest){
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.property = property;
        this.guest = guest;
        this.status = BookingStatus.OPEN;
    }

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public Booking setCheckinDate(LocalDate startDate) {
        this.checkinDate = startDate;
        return this;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public Booking setCheckoutDate(LocalDate endDate) {
        this.checkoutDate = endDate;
        return this;
    }

    public Property getProperty() {
        return property;
    }

    public Booking setProperty(Property property) {
        this.property = property;
        return this;
    }

    public Guest getGuest() {
        return guest;
    }

    public Booking setGuest(Guest guest) {
        this.guest = guest;
        return this;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Booking setStatus(BookingStatus status) {
        this.status = status;
        return this;
    }
}
