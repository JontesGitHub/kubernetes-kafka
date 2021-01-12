package microservice.payment_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {
    private String id;
    private String cardNr;
    private int amount;
    private Status status;
}
