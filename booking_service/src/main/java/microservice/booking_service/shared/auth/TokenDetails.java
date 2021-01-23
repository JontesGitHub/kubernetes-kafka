package microservice.booking_service.shared.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/***
 * Entity of a TokenDetails.
 * This is what gets persisted to the Database.
 * MongoDB collection = TokenDetails
 */
@Data
@RequiredArgsConstructor
public class TokenDetails {
    private String id;
    private final String token;
    private final Long createdAt;
}
