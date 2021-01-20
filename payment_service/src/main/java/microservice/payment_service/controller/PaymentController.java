package microservice.payment_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.payment_service.controller.request.PaymentRequest;
import microservice.payment_service.service.PaymentService;
import microservice.payment_service.shared.auth.AuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final AuthenticationFilter authenticationFilter;

    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(
            @Valid @RequestBody PaymentRequest paymentRequest,
            @RequestHeader(name="Authorization") String token
    ) {
        try {
            if (authenticationFilter.doTokenFilter(token)) {
                String currentUserId = authenticationFilter.getCurrentUserId();
                paymentService.createPayment(paymentRequest, currentUserId);
                return ResponseEntity.ok("Payment was created.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.status(500).body("Unexpected error happened.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validationExceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
