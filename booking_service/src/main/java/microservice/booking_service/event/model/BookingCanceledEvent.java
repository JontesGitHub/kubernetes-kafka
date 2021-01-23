package microservice.booking_service.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Object to represent a canceled booking event
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCanceledEvent extends Event {
    private String paymentId;
}
