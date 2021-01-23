package microservice.booking_service.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.booking_service.shared.DateSpan;

/***
 * Object to represent a payment successful event
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSucceededEvent extends Event {
    private String paymentId;
    private String userId;
    private String carId;
    private DateSpan dateSpan;
}
