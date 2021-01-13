package microservice.payment_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.payment_service.event.model.BookingCanceledEvent;
import microservice.payment_service.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class EventSubscriber {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = "topic.booking.canceled",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(BookingCanceledEvent event) {
        paymentService.handleIncomingEvent(event);
    }
}
