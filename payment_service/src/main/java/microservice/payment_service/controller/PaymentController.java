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

/***
 * Controller for Payment-Microservice containing endpoints
 */
@RequiredArgsConstructor
@RestController
public class PaymentController {
    private final PaymentService paymentService;
    private final AuthenticationFilter authenticationFilter;

    /***
     * Endpoint to create a payment
     * @param paymentRequest request body of a payment, this get validated
     * @param token token for auth
     * @return a string ("Payment was created.") if it was successful otherwise throws an error
     */
    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(
            @Valid @RequestBody PaymentRequest paymentRequest,
            @RequestHeader(name="Authorization") String token
    ) {
        authenticationFilter.doTokenFilter(token);
        String currentUserId = authenticationFilter.getCurrentUserId();

        paymentService.createPayment(paymentRequest, currentUserId);

        return ResponseEntity.ok("Payment was created.");
    }

    /***
     * Exception for handligen validation errors
     * @param ex exception thrown
     * @return a response of the error
     */
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
