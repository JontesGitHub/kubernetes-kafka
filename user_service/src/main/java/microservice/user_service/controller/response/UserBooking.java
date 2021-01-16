package microservice.user_service.controller.response;

import lombok.Data;
import microservice.user_service.shared.DateSpan;

@Data
public class UserBooking {
    private String carId;
    private DateSpan dateSpan;
}
