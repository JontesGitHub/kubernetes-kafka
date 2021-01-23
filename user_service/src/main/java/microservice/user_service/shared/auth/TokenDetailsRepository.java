package microservice.user_service.shared.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for TokenDetails object.
 * It's for a MongoDB database.
 */
@Repository
public interface TokenDetailsRepository extends MongoRepository<TokenDetails, String> {
}
