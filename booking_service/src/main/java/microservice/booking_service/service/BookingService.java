package microservice.booking_service.service;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.event.EventPublisher;
import microservice.booking_service.event.model.BookingCanceledEvent;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.model.Booking;
import microservice.booking_service.repository.BookingRepository;
import microservice.booking_service.shared.DateSpan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {

    @Value("${topic.booking.canceled}")
    private String topic;

    private final BookingRepository bookingRepository;
    private final EventPublisher eventPublisher;

    public void handleIncomingEvent(PaymentSucceededEvent event) {
        saveBooking(new Booking(event));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Map<String, List<DateSpan>> getAllCurrentBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        Map<String, List<DateSpan>> carWithBookedDates = new HashMap<>();

        filterOldBookings(bookings)
                .forEach(booking -> {
                    if (carWithBookedDates.get(booking.getCarId()) == null) {
                        List<DateSpan> dateSpans = new ArrayList<>();
                        dateSpans.add(booking.getDateSpan());
                        carWithBookedDates.put(booking.getCarId(), dateSpans);
                    } else {
                        List<DateSpan> dateSpans = carWithBookedDates.get(booking.getCarId());
                        dateSpans.add(booking.getDateSpan());
                        carWithBookedDates.put(booking.getCarId(), dateSpans);
                    }
                });

        return carWithBookedDates;
    }

    public void cancelBooking(String bookingId, String userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Requested booking id does not exist."
                        )
                );

        if (!userId.equals(booking.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The Booking can not be canceled by the current logged in user."
            );
        }

        eventPublisher.publish(topic, new BookingCanceledEvent(booking.getPaymentId()));

        bookingRepository.delete(booking);
    }

    private void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    private List<Booking> filterOldBookings(List<Booking> bookings) {
        LocalDate now = LocalDate.now();

        return bookings.stream()
                .filter(b ->
                        b.getDateSpan().getFrom().isAfter(now) ||
                                (b.getDateSpan().getFrom().isBefore(now) && b.getDateSpan().getTo().isAfter(now))
                )
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findAllByUserId(userId);
    }
}
