package mono.monolith.payment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.monolith.event.EventPublisher;
import mono.monolith.event.model.PaymentSucceededEvent;
import mono.monolith.payment_service.controller.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${topic.payment.successful}")
    private String topic;

    private final EventPublisher eventPublisher;

    public void createPayment(PaymentRequest paymentRequest) {
        //TODO: is car available at the dateSpan ? ok : throw err
        //TODO: is card valid ? charge card : throw err
        //TODO: send successfulPaymentEvent

        log.info(paymentRequest.toString());
        eventPublisher.publish(topic, new PaymentSucceededEvent(paymentRequest.getCarId(), paymentRequest.getDateSpan()));
    }
}
