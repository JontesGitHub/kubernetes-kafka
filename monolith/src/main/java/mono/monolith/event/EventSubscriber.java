package mono.monolith.event;

import lombok.RequiredArgsConstructor;
import mono.monolith.booking_service.service.BookingService;
import mono.monolith.event.model.BookingCanceledEvent;
import mono.monolith.event.model.PaymentSucceededEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventSubscriber {

    private final BookingService bookingService;

    @KafkaListener(
            topics = "topic.payment.successful",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(PaymentSucceededEvent event) {
        log.info("Message received: " + event);
        bookingService.handleIncomingEvent(event);
    }

    @KafkaListener(
            topics = "test4",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(BookingCanceledEvent event) {
        log.info("Message received: " + event.getId());
    }
}
