package mono.monolith.car_service.controller;

import lombok.RequiredArgsConstructor;
import mono.monolith.event.EventPublisher;
import mono.monolith.event.model.PaymentSuccededEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CarController {

    private final EventPublisher eventPublisher;


    @GetMapping("/cars")
    public String testKafka() {
        eventPublisher.publish("test3", new PaymentSuccededEvent("hello"));
        return "event sent";
    }
}
