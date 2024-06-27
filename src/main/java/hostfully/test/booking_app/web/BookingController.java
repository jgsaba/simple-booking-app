package hostfully.test.booking_app.web;

import hostfully.test.booking_app.domain.dto.booking.BookingAckDTO;
import hostfully.test.booking_app.domain.dto.booking.BookingDTO;
import hostfully.test.booking_app.domain.dto.booking.UpdateBookingDTO;
import hostfully.test.booking_app.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public BookingAckDTO getBooking(@PathVariable Long id){
        return bookingService.getBooking(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingAckDTO createBooking(@RequestBody @Valid BookingDTO bookingDTO){
        return bookingService.createBooking(bookingDTO);
    }

    @PatchMapping("/rebook/{id}")
    public BookingAckDTO rebook(@PathVariable Long id){
        return bookingService.rebook(id);
    }

    @PatchMapping("/cancel/{id}")
    public BookingAckDTO cancel(@PathVariable Long id){
        return bookingService.cancelBooking(id);
    }

    @PutMapping("/{id}")
    public BookingAckDTO updateBooking(@PathVariable Long id, @RequestBody @Valid UpdateBookingDTO bookingDTO){
        return bookingService.updateBooking(id, bookingDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id){
        bookingService.delete(id);
    }
}
