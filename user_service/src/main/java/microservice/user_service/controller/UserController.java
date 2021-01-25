package microservice.user_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.user_service.shared.auth.JwtUtils;
import microservice.user_service.controller.request.Login;
import microservice.user_service.model.User;
import microservice.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/***
 * Controller for User-Microservice containing endpoints
 */
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /***
     *
     * @param login the request login object
     * @return a string "Successful Login. Token: " plus a token if successful
     */
    @PostMapping("/users/login")
    public ResponseEntity<String> login(@Valid @RequestBody Login login) {
            String token = userService.login(login);
            return ResponseEntity.ok("Successful Login. Token: " + token);
    }

    /***
     * Sign up endpoint, saves a User object
     * @param user object from request
     * @return a string "Successful Registration." if successful
     */
    @PostMapping("/users")
    public ResponseEntity<String> registration(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("Successful Registration.");
    }

    /***
     * Exception for handling validation errors
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
