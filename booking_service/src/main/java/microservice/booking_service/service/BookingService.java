package microservice.booking_service.service;

import lombok.RequiredArgsConstructor;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.model.Booking;
import microservice.booking_service.repository.BookingRepository;
import microservice.booking_service.shared.DateSpan;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public void handleIncomingEvent(PaymentSucceededEvent event) {
        saveBooking(new Booking(event));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public boolean checkAvailabilityOfCar(String carId, DateSpan dateSpan) {
        // TODO: check if its available
        for (DateSpan bookingDate : getBookingDatesByCar(carId)) {

            if (isBetweenOrEquals(dateSpan.getFrom(), bookingDate.getFrom(), bookingDate.getTo()) ||
                    isBetweenOrEquals(dateSpan.getTo(), bookingDate.getFrom(), bookingDate.getTo())) {
                return false;
            }
        }
        return true;
    }

    public List<Booking> getAllCurrentBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return filterOldBookings(bookings);
    }

    private void saveBooking(Booking booking) {
        Booking savedBooking = bookingRepository.save(booking);
        // New Booking Added Event?
    }

    private List<DateSpan> getBookingDatesByCar(String carId) {
        List<Booking> bookings = bookingRepository.findByCarId();

        return filterOldBookings(bookings).stream()
                .map(Booking::getDateSpan)
                .collect(Collectors.toList());
    }

    private boolean isBetweenOrEquals(LocalDate checker, LocalDate from, LocalDate to) {
        return checker.isEqual(from) || checker.isEqual(to) || (checker.isAfter(from) && checker.isBefore(to));
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
}
