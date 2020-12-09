package mono.monolith.payment_service.controller;

import lombok.RequiredArgsConstructor;
import mono.monolith.payment_service.controller.request.PaymentRequest;
import mono.monolith.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payments")
    public String createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.createPayment(paymentRequest);
        return "worked";
    }
}
