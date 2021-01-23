package microservice.payment_service.shared.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/***
 * A authentication filter for security logic
 */
@RequiredArgsConstructor
@Component
public class AuthenticationFilter {
    private final TokenDetailsRepository tokenDetailsRepository;
    private final JwtUtils jwtUtils;

    private String currentUserId;

    /***
     * Validates and filter incoming requests and saves the userId from the token if successful
     * @param token the token from a request to validate
     */
    public void doTokenFilter(String token) {
        String parsedToken = parseToken(token);

        jwtUtils.validateJwtToken(parsedToken);

        if (!tokenDetailsRepository.existsByToken(parsedToken)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token doesn't exist in DB");
        }

        currentUserId = jwtUtils.getUserIdFromJwtToken(parsedToken);
    }

    /***
     * Gets the userId after the filter is successful
     * @return the userId for a successful token
     */
    public String getCurrentUserId() {
        return currentUserId;
    }

    /***
     * Parses a token if it starts with "Bearer "
     * @param token token to parse
     * @return a token without "Bearer "
     */
    private String parseToken(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
