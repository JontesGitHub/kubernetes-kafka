package microservice.booking_service.shared.auth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenDetailsRepository extends MongoRepository<TokenDetails, String> {
    boolean existsByToken(String token);
}
