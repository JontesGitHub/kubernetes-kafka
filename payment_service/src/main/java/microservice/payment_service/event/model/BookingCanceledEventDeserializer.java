package microservice.payment_service.event.model;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class BookingCanceledEventDeserializer extends JsonDeserializer<BookingCanceledEvent> {

    public BookingCanceledEventDeserializer() {
        super(BookingCanceledEvent.class);
    }
}
