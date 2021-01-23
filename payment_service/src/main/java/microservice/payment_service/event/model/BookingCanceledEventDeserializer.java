package microservice.payment_service.event.model;

import org.springframework.kafka.support.serializer.JsonDeserializer;

/***
 * Custom JSON Deserializer class for BookingCanceledEvent class
 */
public class BookingCanceledEventDeserializer extends JsonDeserializer<BookingCanceledEvent> {

    /***
     * Constructor calling the super class constructor
     */
    public BookingCanceledEventDeserializer() {
        super(BookingCanceledEvent.class);
    }
}
