package microservice.booking_service.event.model;

import org.springframework.kafka.support.serializer.JsonDeserializer;

/***
 * Custom JSON Deserializer class for PaymentSucceededEvent class
 */
public class PaymentSucceededEventDeserializer extends JsonDeserializer<PaymentSucceededEvent> {

    /***
     * Constructor calling the super class constructor
     */
    public PaymentSucceededEventDeserializer() {
        super(PaymentSucceededEvent.class);
    }
}
