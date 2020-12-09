package mono.monolith.booking_service.controller;

import lombok.RequiredArgsConstructor;
import mono.monolith.booking_service.model.Booking;
import mono.monolith.booking_service.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

}
