package microservice.booking_service.shared.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter {
    private final TokenDetailsRepository tokenDetailsRepository;
    private final JwtUtils jwtUtils;

    private String currentUserId;

    public boolean doTokenFilter(String token) throws Exception {
        String parsedToken = parseToken(token);

        if (!jwtUtils.validateJwtToken(parsedToken)) {
            throw new Exception("Token is invalid");
        }

        if (!tokenDetailsRepository.existsByToken(parsedToken)) {
            throw new Exception("Token doesn't exist in DB");
        }

        currentUserId = jwtUtils.getUserIdFromJwtToken(parsedToken);
        return true;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    private String parseToken(String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
