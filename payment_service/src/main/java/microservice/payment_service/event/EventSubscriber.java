//package microservice.payment_service.event;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class EventSubscriber {
//
//    @KafkaListener(
//            topics = "test4",
//            containerFactory = "kafkaListenerContainerFactory"
//    )
//    public void consumeMessage(BookingCanceledEvent event) {
//        log.info("Message received: " + event.getId());
//    }
//}
