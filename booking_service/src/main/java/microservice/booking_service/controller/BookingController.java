package microservice.booking_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.model.Booking;
import microservice.booking_service.service.BookingService;
import microservice.booking_service.shared.DateSpan;
import microservice.booking_service.shared.auth.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;
    private final AuthenticationFilter authenticationFilter;

    @Value("${booking.service.api-key}")
    private String API_KEY;

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookingsByUser(@RequestHeader(name="Authorization") String token) {
        authenticationFilter.doTokenFilter(token);
        String currentUserId = authenticationFilter.getCurrentUserId();

        return ResponseEntity.ok(bookingService.getBookingsByUser(currentUserId));
    }

    @GetMapping("/bookings/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/bookings/current")
    public Map<String, List<DateSpan>> getAllCurrentBookings(@RequestHeader(name = "api-key") String apiKey) {
        if (!apiKey.equals(API_KEY)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "api-key is needed.");
        }

        return bookingService.getAllCurrentBookings();
    }

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
}
