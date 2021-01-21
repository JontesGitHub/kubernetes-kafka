package microservice.booking_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.model.Booking;
import microservice.booking_service.service.BookingService;
import microservice.booking_service.shared.DateSpan;
import microservice.booking_service.shared.auth.AuthenticationFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;
    private final AuthenticationFilter authenticationFilter;

    @GetMapping("/bookings/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

//    @PostMapping("/bookings/cars/{carId}/available") // internal check from payment service
//    public Boolean checkAvailabilityOfCar(@PathVariable String carId, @RequestBody DateSpan dateSpan) {
//        return bookingService.checkAvailabilityOfCar(carId, dateSpan);
//    }

//    @GetMapping("/bookings/current") //use for internal
//    public List<Booking> getAllCurrentBookings() {
//        return bookingService.getAllCurrentBookings();
//    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable String bookingId,
            @RequestHeader(name="Authorization") String token
    ) {
        authenticationFilter.doTokenFilter(token);
        String currentUserId = authenticationFilter.getCurrentUserId();

        bookingService.cancelBooking(bookingId, currentUserId);
        return ResponseEntity.ok("booking is canceled and deleted");
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookingsByUser(@RequestHeader(name="Authorization") String token) {
        authenticationFilter.doTokenFilter(token);
        String currentUserId = authenticationFilter.getCurrentUserId();

        return ResponseEntity.ok(bookingService.getBookingsByUser(currentUserId));
    }

}
