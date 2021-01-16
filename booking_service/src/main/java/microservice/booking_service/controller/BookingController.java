package microservice.booking_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.model.Booking;
import microservice.booking_service.service.BookingService;
import microservice.booking_service.shared.DateSpan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/bookings/cars/{carId}/available") // internal check from payment service
    public Boolean checkAvailabilityOfCar(@PathVariable String carId, @RequestBody DateSpan dateSpan) {
        return bookingService.checkAvailabilityOfCar(carId, dateSpan);
    }

    @GetMapping("/bookings/current") //use for internal
    public List<Booking> getAllCurrentBookings() {
        return bookingService.getAllCurrentBookings();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public String cancelBooking(@PathVariable String bookingId) throws Exception {
        bookingService.cancelBooking(bookingId);
        return "booking is canceled";
    }

    @GetMapping("/bookings/users/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable String userId) {
        return bookingService.getBookingsByUser(userId);
    }

}
