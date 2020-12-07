package mono.monolith.event;

import mono.monolith.event.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public void publish(final String topic, final Event event) {
        kafkaTemplate.send(topic, event);
        log.info("Message Published: " + event);
    }

}
