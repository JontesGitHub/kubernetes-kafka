package microservice.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.shared.DateSpan;

/***
 * Entity of a Booking.
 * This is what gets persisted to the Database.
 * MongoDB collection = Booking
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String carId;
    private String userId;
    private String paymentId;
    private DateSpan dateSpan;

    /***
     * Creates a booking from a PaymentSucceededEvent object
     * @param event the event that will map into a Booking
     */
    public Booking(PaymentSucceededEvent event) {
        this.carId = event.getCarId();
        this.dateSpan = event.getDateSpan();
        this.paymentId = event.getPaymentId();
        this.userId = event.getUserId();
    }
}
