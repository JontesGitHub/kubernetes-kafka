package mono.monolith.controller;

import mono.monolith.event.EventPublisher;
import mono.monolith.event.model.BookingCanceledEvent;
import mono.monolith.event.model.PaymentSuccededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventTestController {

    private final EventPublisher eventPublisher;

    @GetMapping("/pay/{message}")
    public String doSend(@PathVariable String message) {
        eventPublisher.publish("test.topic2", new PaymentSuccededEvent(message));
        return "message sent";
    }

    @GetMapping("/cancel/{message}")
    public String doSend2(@PathVariable String message) {
        eventPublisher.publish("test.topic3", new BookingCanceledEvent(message));
        return "message sent";
    }
}
