package microservice.payment_service.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import microservice.payment_service.shared.DateSpan;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSucceededEvent extends Event {
    private String carId;
    private DateSpan dateSpan;
}
