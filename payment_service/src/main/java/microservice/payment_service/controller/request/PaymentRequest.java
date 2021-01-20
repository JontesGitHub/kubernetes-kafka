package microservice.payment_service.controller.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import microservice.payment_service.shared.DateSpan;

import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class PaymentRequest {
    @NotBlank(message = "Property carId is mandatory")
    private final String carId;

    private final DateSpan dateSpan;

    @NotBlank(message = "Property cardNr is mandatory")
    private final String cardNr;
}
