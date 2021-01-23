package microservice.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.user_service.controller.request.Login;
import microservice.user_service.model.User;
import microservice.user_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

/***
 * Service class for all the User logic
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    /***
     * Handles a login, and checks it creadentials
     * @param login the requested Login object
     * @return a token
     */
    public String login(Login login) {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "The Username don't exist"
                        )
                );

        if (!isPasswordCorrect(login.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The Password is not correct. Try again."
            );
        }
        log.info("New Login: ID: {} User: {} was logged in at {}", user.getId(), user.getUsername(), LocalTime.now().toString().substring(0,8));

        return tokenService.getToken(user.getId());
    }

    /***
     * Checks if the password is correct
     * @param password password to check
     * @param actual the actual password from database
     * @return true if the password is correct
     */
    private boolean isPasswordCorrect(String password, String actual) {
        return password.equals(actual);
    }

    /***
     * Persists a new User registration to the database
     * @param user to save
     */
    public void register(User user) {
        userRepository.save(user);
    }
}
