package microservice.booking_service.service;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.model.Booking;
import microservice.booking_service.repository.BookingRepository;
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
