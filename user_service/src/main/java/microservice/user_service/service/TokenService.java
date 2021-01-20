package microservice.user_service.service;

import lombok.RequiredArgsConstructor;
import microservice.user_service.shared.auth.JwtUtils;
import microservice.user_service.shared.auth.TokenDetailsRepository;
import microservice.user_service.shared.auth.TokenDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final JwtUtils jwtUtils;
    private final TokenDetailsRepository tokenDetailsRepository;

    public String getToken(String userId) {
        String token = jwtUtils.generateJwtToken(userId);

        tokenDetailsRepository.save(new TokenDetails(token, System.currentTimeMillis()));

        return token;
    }
}
