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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Service class for all the Payment logic
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    @Value("${topic.payment.successful}")
    private String topic;

    private final EventPublisher eventPublisher;
    private final PaymentRepository paymentRepository;

    private final int PRICE_PER_DAY = 500;

    /***
     * Handles the logic for when an incoming BookingCanceledEvent comes
     * @param event that will be handled
     */
    public void handleIncomingEvent(BookingCanceledEvent event) {
        paymentRepository.findById(event.getPaymentId())
                .ifPresentOrElse(
                        payment -> repayPayment(payment),
                        () -> log.error("No payment found by id: {} from BookingCanceledEvent.", event.getPaymentId())
                );
    }

    /***
     * Makes an fake repay of a payment
     * @param payment the payment to refund
     */
    private void repayPayment(Payment payment) {
        log.info("Payment with ID: {}, was repaid to Card: {}, by amount: {}", payment.getId(), payment.getCardNr(), payment.getAmount());
        payment.setStatus(Status.REPAID);
        paymentRepository.save(payment);
    }

    /***
     * Creates a payment, if successful saves to database and publish a PaymentSucceededEvent
     * @param paymentRequest the request object
     * @param userId the userId from the token
     * @throws ResponseStatusException
     */
    public void createPayment(PaymentRequest paymentRequest, String userId) throws ResponseStatusException {
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

        eventPublisher.publish(topic, new PaymentSucceededEvent(
                savedPayment.getId(),
                userId,
                paymentRequest.getCarId(),
                paymentRequest.getDateSpan())
        );
    }

    /***
     * Method to calculate the total price for the payment
     * @param dateSpan the rental dates
     * @return total price
     */
    private int calculateAmount(DateSpan dateSpan) {
        long daysRented = dateSpan.getFrom().datesUntil(dateSpan.getTo()).count();
        return (int) daysRented * PRICE_PER_DAY;
    }

    /***
     * Method makes the payment
     * @param amount the total price for the payment
     * @param cardNr the card NR used to pay
     * @return true if successful
     */
    private boolean makePayment(int amount, String cardNr) {
        // Making payment...
        if (!isCardValid(cardNr)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payment failed, invalid cardNr");
        }
        log.info("Card: {} was charged with {}", cardNr, amount);
        return true;
    }

    /***
     * Checks if a card NR is valid
     * @param cardNr cardNr to calidate
     * @return true if successful
     */
    private boolean isCardValid(String cardNr) {
        Pattern pattern = Pattern.compile("5[1-5][0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
        Matcher matcher = pattern.matcher(cardNr);
        return matcher.matches();
    }
}
