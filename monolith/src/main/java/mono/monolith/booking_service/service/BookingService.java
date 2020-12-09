package mono.monolith.booking_service.service;

import lombok.RequiredArgsConstructor;
import mono.monolith.booking_service.model.Booking;
import mono.monolith.booking_service.repository.BookingRepository;
import mono.monolith.event.model.PaymentSucceededEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public void handleIncomingEvent(PaymentSucceededEvent event) {
        saveBooking(new Booking(event));
    }

    private void saveBooking(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        // New Booking Added Event
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
