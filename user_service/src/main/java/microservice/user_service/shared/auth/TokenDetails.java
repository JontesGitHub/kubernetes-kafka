package microservice.user_service.shared.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenDetails {
    private String id;
    private final String token;
    private final Long createdAt;
}
