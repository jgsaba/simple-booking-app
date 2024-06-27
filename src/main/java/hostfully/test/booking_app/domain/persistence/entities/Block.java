package hostfully.test.booking_app.domain.persistence.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "DATE")
    LocalDate startDate;

    @Column(columnDefinition = "DATE")
    LocalDate endDate;
    @ManyToOne
    Property property;

    public Long getId() {
        return id;
    }

    public Block setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Block setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Block setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public Property getProperty() {
        return property;
    }

    public Block setProperty(Property property) {
        this.property = property;
        return this;
    }
}
