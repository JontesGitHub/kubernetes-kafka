package microservice.payment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.payment_service.controller.request.PaymentRequest;
import microservice.payment_service.event.EventPublisher;
import microservice.payment_service.event.model.BookingCanceledEvent;
import microservice.payment_service.event.model.PaymentSucceededEvent;
import microservice.payment_service.model.Payment;
import microservice.payment_service.model.Status;
import microservice.payment_service.repository.PaymentRepository;
import microservice.payment_service.shared.DateSpan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${topic.payment.successful}")
    private String topic;
//    @Value("${booking-host-url}")
//    private final String bookingHostUrl;

    private final EventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;

    private final int PRICEPERDAY = 500;

    public void handleIncomingEvent(BookingCanceledEvent event) {
        paymentRepository.findById(event.getPaymentId())
                .ifPresentOrElse(
                        payment -> repayPayment(payment),
                        () -> log.error("No payment found by id: {} from BookingCanceledEvent.", event.getPaymentId())
                );
    }

    private void repayPayment(Payment payment) {
        log.info("Payment with ID: {}, was repaid to Card: {}, by amount: {}", payment.getId(), payment.getCardNr(), payment.getAmount());
        payment.setStatus(Status.REPAID);
        paymentRepository.save(payment);
    }

    public void createPayment(PaymentRequest paymentRequest) throws ResponseStatusException {
        //TODO: is car available at the dateSpan ? ok : throw err
//        isCarAvailableToRent(paymentRequest.getCarId(), paymentRequest.getDateSpan());

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

//        log.info(paymentRequest.toString());
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
        log.info("Card: {} was charged with {}", cardNr, amount);
        return true;
    }

    private boolean isCardValid(String cardNr) {
        Pattern pattern = Pattern.compile("5[1-5][0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        Matcher matcher = pattern.matcher(cardNr);
        return matcher.matches();
    }

    private void isCarAvailableToRent(String carId, DateSpan dateSpan) {
        // TODO: Make http anrop to bookings

//        Boolean isAvailable = WebClient.create()
//                .post()
//                .uri(uriBuilder ->
//                    uriBuilder
//                            .host(bookingHostUrl)
//                            .path("/bookings/cars/{carId}")
//                            .build(carId)
//                )
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(dateSpan, DateSpan.class)
//                .retrieve()
//                .bodyToMono(Boolean.class)
//                .block();
//
//        if(!isAvailable) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed, Car is not available at the requested dates");
//        }
    }
}
