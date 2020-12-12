package microservice.payment_service.controller.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import microservice.payment_service.shared.DateSpan;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PaymentRequest {
    private final String carId;
    private final DateSpan dateSpan;
    private final String cardNr;
//    private final String userId;
}
