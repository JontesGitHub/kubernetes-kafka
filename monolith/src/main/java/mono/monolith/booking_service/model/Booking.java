package mono.monolith.booking_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mono.monolith.event.model.PaymentSucceededEvent;
import mono.monolith.shared.DateSpan;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String id;
    private String carId;
//    private String userId;
    private DateSpan dateSpan;

    public Booking(PaymentSucceededEvent event) {
        this.carId = event.getCarId();
        this.dateSpan = event.getDateSpan();
    }
}
