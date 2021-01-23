package microservice.booking_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.booking_service.event.model.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/***
 * Event Publisher object for Kafka events
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    /***
     * Sends a Kafka Event message to a topic
     * @param topic the topic we want to send this event to
     * @param event the event message we want to send
     */
    public void publish(final String topic, final Event event) {
        kafkaTemplate.send(topic, event);
        log.info("Message Published: " + event);
    }
}
