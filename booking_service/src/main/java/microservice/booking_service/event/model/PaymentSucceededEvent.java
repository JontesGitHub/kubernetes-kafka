package microservice.booking_service.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.booking_service.shared.DateSpan;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSucceededEvent extends Event {
    private String paymentId;
    private String carId;
    private DateSpan dateSpan;
}
