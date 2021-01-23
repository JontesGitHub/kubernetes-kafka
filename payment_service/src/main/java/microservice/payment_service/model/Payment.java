package microservice.payment_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Entity of a Payment.
 * This is what gets persisted to the Database.
 * MongoDB collection = Payment
 */
@Data
@NoArgsConstructor
public class Payment {
    private String id;
    private String cardNr;
    private int amount;
    private Status status;
}
