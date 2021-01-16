package microservice.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.user_service.controller.request.Login;
import microservice.user_service.controller.response.UserBooking;
import microservice.user_service.model.User;
import microservice.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void login(Login login) throws Exception {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new Exception("The Username don't exist"));

        if (!isPasswordCorrect(login.getPassword(), user.getPassword())) {
            throw new Exception("The Password is not correct. Try again.");
        }
        log.info("New Login: ID: {} User: {} was logged in at {}", user.getId(), user.getUsername(), LocalTime.now().toString().substring(0,8));
    }

    private boolean isPasswordCorrect(String password, String actual) {
        return password.equals(actual);
    }

    public void register(User user) {
        userRepository.save(user);
    }

    public List<UserBooking> getBookingsByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("The Username don't exist"));

        // TODO: http anrop till booking hämta alla med samma userId
        // lägg till i payment create ett userId
        // i event user id
        // i booking model classen user id
        return null;
    }
}
