package hostfully.test.booking_app.service;

import hostfully.test.booking_app.domain.dto.booking.BookingAckDTO;
import hostfully.test.booking_app.domain.dto.booking.BookingDTO;
import hostfully.test.booking_app.domain.dto.booking.UpdateBookingDTO;
import hostfully.test.booking_app.domain.exceptions.OverlappingBookingException;
import hostfully.test.booking_app.domain.exceptions.entities.BookingNotFoundException;
import hostfully.test.booking_app.domain.exceptions.entities.GuestNotFoundException;
import hostfully.test.booking_app.domain.exceptions.entities.PropertyNotFoundException;
import hostfully.test.booking_app.domain.persistence.entities.Booking;
import hostfully.test.booking_app.domain.persistence.entities.BookingStatus;
import hostfully.test.booking_app.domain.persistence.entities.Guest;
import hostfully.test.booking_app.domain.persistence.entities.Property;
import hostfully.test.booking_app.domain.persistence.repositories.BlockRepository;
import hostfully.test.booking_app.domain.persistence.repositories.BookingRepository;
import hostfully.test.booking_app.domain.persistence.repositories.GuestRepository;
import hostfully.test.booking_app.domain.persistence.repositories.PropertyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final PropertyRepository propertyRepository;

    private final BlockRepository blockRepository;

    public BookingService(BookingRepository bookingRepository, GuestRepository guestRepository, PropertyRepository propertyRepository, BlockRepository blockRepository) {
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
        this.propertyRepository = propertyRepository;
        this.blockRepository = blockRepository;
    }

    public BookingAckDTO cancelBooking(Long id) {
        Booking booking = findBookingById(id);

        if (booking.getStatus().equals(BookingStatus.CHECKED_OUT)) {
            throw new DataIntegrityViolationException("Cannot cancel a booking that has already been checked-out");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return BookingAckDTO.from(bookingRepository.save(booking));
    }

    public BookingAckDTO updateBooking(Long id, UpdateBookingDTO updateBookingDTO){

        if (updateBookingDTO.checkinDate().isAfter(updateBookingDTO.checkoutDate())
                || updateBookingDTO.checkinDate().isEqual(updateBookingDTO.checkoutDate())){
            throw new DataIntegrityViolationException("Cannot book with a checkout date before or equal to checkin date");
        }

        Booking booking = findBookingById(id);

        if (booking.getStatus().equals(BookingStatus.CANCELLED)){
            throw new DataIntegrityViolationException("Cannot update a cancelled booking");
        }

        if (overlapsForUpdate(booking)){
            throw new OverlappingBookingException();
        }

        booking.setCheckinDate(updateBookingDTO.checkinDate())
                .setCheckoutDate(updateBookingDTO.checkoutDate())
                .getGuest().setName(updateBookingDTO.guestName());

        return BookingAckDTO.from(bookingRepository.save(booking));
    }

    public BookingAckDTO rebook(Long id){

        Booking booking = findBookingById(id);

        if (!booking.getStatus().equals(BookingStatus.CANCELLED)){
            throw new DataIntegrityViolationException("Cannot rebook a non-cancelled booking");
        }

        if (overlaps(booking)){
            throw new OverlappingBookingException();
        }

        booking.setStatus(BookingStatus.OPEN);
        bookingRepository.save(booking);

        return BookingAckDTO.from(booking);
    }

    public void delete(Long id){
        bookingRepository.deleteById(id);
    }

    public BookingAckDTO getBooking(Long id){
        return BookingAckDTO.from(findBookingById(id));
    }

    public BookingAckDTO createBooking(BookingDTO bookingDTO){

        if (overlaps(bookingDTO)){
            throw new OverlappingBookingException();
        }

        Property property = propertyRepository.findById(bookingDTO.propertyId())
                .orElseThrow(PropertyNotFoundException::new);

        Guest guest = guestRepository.findById(bookingDTO.guestId())
                .orElseThrow(GuestNotFoundException::new);

        Booking booking = bookingRepository.save(new Booking(
                bookingDTO.checkinDate(),
                bookingDTO.checkoutDate(),
                property,
                guest));

        return BookingAckDTO.from(booking);
    }

    public void cancelAllOverlappingBookings(Long propertyId, LocalDate checkinDate, LocalDate checkoutDate){
        bookingRepository.saveAll(bookingRepository.findOverlappingBookings(propertyId, checkinDate, checkoutDate)
                .stream()
                .map(booking -> booking.setStatus(BookingStatus.CANCELLED))
                .collect(Collectors.toSet()));
    }

    private Booking findBookingById(Long id){
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFoundException::new);
    }

    private boolean overlaps(BookingDTO bookingDTO){
        return overlaps(bookingDTO.propertyId(), bookingDTO.checkinDate(), bookingDTO.checkoutDate());
    }

    private boolean overlaps(Booking booking){
        return overlaps(booking.getProperty().getId(), booking.getCheckinDate(), booking.getCheckoutDate());
    }

    private boolean overlapsForUpdate(Booking booking){
        return bookingRepository.checkOverlappingBookingsForUpdate(booking.getId(), booking.getProperty().getId(), booking.getCheckinDate(), booking.getCheckoutDate())
                || blockRepository.checkOverlappingBlocks(booking.getProperty().getId(), booking.getCheckinDate(), booking.getCheckoutDate());
    }

    private boolean overlaps(Long propertyId, LocalDate checkinDate, LocalDate checkoutDate){
        return bookingRepository.checkOverlappingBookings(propertyId, checkinDate, checkoutDate)
                || blockRepository.checkOverlappingBlocks(propertyId, checkinDate, checkoutDate);
    }
}
