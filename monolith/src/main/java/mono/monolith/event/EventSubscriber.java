package mono.monolith.event;

import mono.monolith.event.model.BookingCanceledEvent;
import mono.monolith.event.model.PaymentSuccededEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventSubscriber {

    @KafkaListener(
            topics = "test3",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(PaymentSuccededEvent event) {
        log.info("Message received: " + event);
    }

    @KafkaListener(
            topics = "test4",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(BookingCanceledEvent event) {
        log.info("Message received: " + event.getId());
    }
}
