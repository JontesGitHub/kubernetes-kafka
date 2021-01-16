package microservice.user_service.controller.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
public class Login {
    @NotBlank(message = "Property username is mandatory")
    private final String username;
    @NotBlank(message = "Property password is mandatory")
    private final String password;
}
