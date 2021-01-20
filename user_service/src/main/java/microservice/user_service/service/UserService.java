package microservice.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.user_service.controller.request.Login;
import microservice.user_service.model.User;
import microservice.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public String login(Login login) throws Exception {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new Exception("The Username don't exist"));

        if (!isPasswordCorrect(login.getPassword(), user.getPassword())) {
            throw new Exception("The Password is not correct. Try again.");
        }
        log.info("New Login: ID: {} User: {} was logged in at {}", user.getId(), user.getUsername(), LocalTime.now().toString().substring(0,8));

        return tokenService.getToken(user.getId());
    }

    private boolean isPasswordCorrect(String password, String actual) {
        return password.equals(actual);
    }

    public void register(User user) {
        userRepository.save(user);
    }

}
