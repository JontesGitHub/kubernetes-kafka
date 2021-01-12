package microservice.payment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.payment_service.controller.request.PaymentRequest;
import microservice.payment_service.event.EventPublisher;
import microservice.payment_service.event.model.PaymentSucceededEvent;
import microservice.payment_service.model.Payment;
import microservice.payment_service.model.Status;
import microservice.payment_service.repository.PaymentRepository;
import microservice.payment_service.shared.DateSpan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${topic.payment.successful}")
    private String topic;

    private final EventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;

    private final int PRICEPERDAY = 500;

    public void createPayment(PaymentRequest paymentRequest) {
        //TODO: is car available at the dateSpan ? ok : throw err
        isCarAvailableToRent(paymentRequest.getCarId(), paymentRequest.getDateSpan());

        Payment payment = new Payment();

        final int amount = calculateAmount(paymentRequest.getDateSpan());
        boolean isSuccessful = makePayment(amount, paymentRequest.getCardNr());

        if (isSuccessful) {
            payment.setStatus(Status.SUCCEEDED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed, Could not make the payment");
        }

        payment.setAmount(amount);
        payment.setCardNr(paymentRequest.getCardNr());
        Payment savedPayment = paymentRepository.save(payment);

        //TODO: send successfulPaymentEvent
        log.info(paymentRequest.toString());
        eventPublisher.publish(topic, new PaymentSucceededEvent(savedPayment.getId(), paymentRequest.getCarId(), paymentRequest.getDateSpan()));
    }

    private int calculateAmount(DateSpan dateSpan) {
        long daysRented = dateSpan.getFrom().datesUntil(dateSpan.getTo()).count();
        return (int) daysRented * PRICEPERDAY;
    }

    private boolean makePayment(int amount, String cardNr) {
        // Making payment
        if (!isCardValid(cardNr)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed, invalid cardNr");
        }

        return true;
    }

    private boolean isCardValid(String cardNr) {
        Pattern pattern = Pattern.compile("5[1-5][0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        Matcher matcher = pattern.matcher(cardNr);
        return matcher.matches();
    }

    private void isCarAvailableToRent(String carId, DateSpan dateSpan) {
        // TODO: Make http anrop to bookings
    }
}
