package microservice.booking_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.service.BookingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/***
 * Event Subscriber object for incoming Kafka events
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class EventSubscriber {

    private final BookingService bookingService;

    /***
     * Consumes a Event from topic: "topic.payment.successful"
     * @param event the incoming event
     */
    @KafkaListener(
            topics = "topic.payment.successful",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(PaymentSucceededEvent event) {
        log.info("Message received: " + event);
        bookingService.handleIncomingEvent(event);
    }
}
