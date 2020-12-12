package microservice.booking_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.booking_service.event.model.PaymentSucceededEvent;
import microservice.booking_service.service.BookingService;
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

//    @KafkaListener(
//            topics = "test4",
//            containerFactory = "kafkaListenerContainerFactory"
//    )
//    public void consumeMessage(BookingCanceledEvent event) {
//        log.info("Message received: " + event.getId());
//    }
}
