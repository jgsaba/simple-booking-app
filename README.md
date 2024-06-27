# Read Me

Application is running on 8080

Swagger URL: http://localhost:8080/swagger-ui/index.html


Initial data is being loaded at app startup:
[DataLoader.java](src%2Fmain%2Fjava%2Fhostfully%2Ftest%2Fbooking_app%2Fconfig%2FDataLoader.java)

I've considered some additional business rules to better guide myself through development:
* Considered that bookings can have the following statuses: [BookingStatus.java](src%2Fmain%2Fjava%2Fhostfully%2Ftest%2Fbooking_app%2Fdomain%2Fpersistence%2Fentities%2FBookingStatus.java)
* Check-in date cannot be equal or greater than check-out date
* Cannot create bookings or blocks with past dates
* Bookings can overlap only if check-in date of booking A is equal check-out date of booking B
* Bookings/blocks overlaps only bookings that are either OPEN or CHECKED_IN
* Creating a block automatically cancels all overlapping bookings (blocks have priority)
* Cannot rebook a non-cancelled booking. For non-cancelled bookings, use update operation
* Likewise, cannot update a cancelled booking. For cancelled booking, use rebook operation

