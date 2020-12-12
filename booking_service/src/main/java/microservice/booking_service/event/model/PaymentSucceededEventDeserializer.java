package microservice.booking_service.event.model;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class PaymentSucceededEventDeserializer extends JsonDeserializer<PaymentSucceededEvent> {

    public PaymentSucceededEventDeserializer() {
        super(PaymentSucceededEvent.class);
    }
}
