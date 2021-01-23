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

/***
 * Service class for all the Booking logic
 */
@RequiredArgsConstructor
@Service
public class BookingService {

    @Value("${topic.booking.canceled}")
    private String topic;

    private final BookingRepository bookingRepository;
    private final EventPublisher eventPublisher;

    /***
     * Handles the logic for when an incoming PaymentSucceededEvent comes
     * @param event that will be handled
     */
    public void handleIncomingEvent(PaymentSucceededEvent event) {
        saveBooking(new Booking(event));
    }

    /***
     * Gets all bookings from the database
     * @return a list of bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /***
     * Gets all current (non old) bookings from database and maps into a map of carId and its booked dates
     * @return a map of carId and its booked dates
     */
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

    /***
     * Handles the logic for canceling a booking
     * @param bookingId Id to find and cancel
     * @param userId the userId from the token that requested this endpoint call
     */
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

    /***
     * Persists a booking to the database
     * @param booking the object to save
     */
    private void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    /***
     * Filters away all the old bookings from a list of bookings
     * @param bookings the list of all bookings
     * @return a result in a list of bookings
     */
    private List<Booking> filterOldBookings(List<Booking> bookings) {
        LocalDate now = LocalDate.now();

        return bookings.stream()
                .filter(b ->
                        b.getDateSpan().getFrom().isAfter(now) ||
                                (b.getDateSpan().getFrom().isBefore(now) && b.getDateSpan().getTo().isAfter(now))
                )
                .collect(Collectors.toList());
    }

    /***
     * Gets all bookings by a Users ID
     * @param userId the userId to search with
     * @return a list of that users bookings
     */
    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findAllByUserId(userId);
    }
}
