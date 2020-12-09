package mono.monolith.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mono.monolith.shared.DateSpan;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSucceededEvent extends Event {
    private String carId;
    private DateSpan dateSpan;
}
