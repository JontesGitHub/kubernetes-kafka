package microservice.user_service.service;

import lombok.RequiredArgsConstructor;
import microservice.user_service.shared.auth.JwtUtils;
import microservice.user_service.shared.auth.TokenDetailsRepository;
import microservice.user_service.shared.auth.TokenDetails;
import org.springframework.stereotype.Service;

/***
 * Service class for handling logic for generating tokens
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtils jwtUtils;
    private final TokenDetailsRepository tokenDetailsRepository;

    /***
     * Creates a JWT token
     * @param userId userId to map to the token
     * @return a token
     */
    public String getToken(String userId) {
        String token = jwtUtils.generateJwtToken(userId);

        tokenDetailsRepository.save(new TokenDetails(token, System.currentTimeMillis()));

        return token;
    }
}
