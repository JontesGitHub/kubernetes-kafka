package microservice.user_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.user_service.controller.request.Login;
import microservice.user_service.controller.response.UserBooking;
import microservice.user_service.model.User;
import microservice.user_service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users/login")
    public String login(@Valid @RequestBody Login login) {
        try {
            userService.login(login);
            return "Successful Login.";

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/users")
    public String registration(@Valid @RequestBody User user) {
        userService.register(user);
        return "Successful Registration.";
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
