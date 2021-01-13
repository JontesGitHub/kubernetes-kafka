package microservice.payment_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.payment_service.controller.request.PaymentRequest;
import microservice.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payments")
    public String createPayment(@RequestBody PaymentRequest paymentRequest) throws ResponseStatusException {
        paymentService.createPayment(paymentRequest);
        return "worked";
    }
}
