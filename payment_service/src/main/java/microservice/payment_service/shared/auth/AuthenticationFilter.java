package microservice.payment_service.shared.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter {
    private final TokenDetailsRepository tokenDetailsRepository;
    private final JwtUtils jwtUtils;

    private String currentUserId;

    public void doTokenFilter(String token) {
        String parsedToken = parseToken(token);

        jwtUtils.validateJwtToken(parsedToken);

        if (!tokenDetailsRepository.existsByToken(parsedToken)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token doesn't exist in DB");
        }

        currentUserId = jwtUtils.getUserIdFromJwtToken(parsedToken);
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    private String parseToken(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
