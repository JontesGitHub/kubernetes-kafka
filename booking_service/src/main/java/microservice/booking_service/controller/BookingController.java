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

/***
 * Controller for Booking-Microservice containing endpoints
 */
@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;
    private final AuthenticationFilter authenticationFilter;

    @Value("${booking.service.api-key}")
    private String API_KEY;

    /***
     * Gets all booking and using the token to fetch all bookings for that user
     * @param token jwt token for auth
     * @return list of bookings
     */
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookingsByUser(@RequestHeader(name="Authorization") String token) {
        authenticationFilter.doTokenFilter(token);
        String currentUserId = authenticationFilter.getCurrentUserId();

        return ResponseEntity.ok(bookingService.getBookingsByUser(currentUserId));
    }

    /***
     * Normal endpoint to just get all bookings
     * @return list of all bookings from database
     */
    @GetMapping("/bookings/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    /***
     * Gets all current bookings carIds and their booked dates.
     * Endpoint is for internal use in microservice cluster.
     * @param apiKey a secret api-key is required
     * @return a map of carIds with their current booked dates (not old ones)
     */
    @GetMapping("/bookings/current")
    public Map<String, List<DateSpan>> getAllCurrentBookings(@RequestHeader(name = "api-key") String apiKey) {
        if (!apiKey.equals(API_KEY)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "api-key is needed.");
        }

        return bookingService.getAllCurrentBookings();
    }

    /***
     * Cancels a booking by an ID
     * @param bookingId id to cancel and delete
     * @param token jwt token for auth
     * @return a string ("booking is canceled and deleted") that it was successful
     */
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
