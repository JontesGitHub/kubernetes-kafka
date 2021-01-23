package microservice.booking_service.shared.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for TokenDetails object.
 * It's for a MongoDB database.
 */
@Repository
public interface TokenDetailsRepository extends MongoRepository<TokenDetails, String> {
    /***
     * Finds if a token exists in database
     * @param token token to see if it exists
     * @return a true if it exists otherwise false
     */
    boolean existsByToken(String token);
}
