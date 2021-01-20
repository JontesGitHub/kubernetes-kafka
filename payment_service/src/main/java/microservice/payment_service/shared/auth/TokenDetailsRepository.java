package microservice.payment_service.shared.auth;

import microservice.payment_service.shared.auth.TokenDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenDetailsRepository extends MongoRepository<TokenDetails, String> {
    boolean existsByToken(String token);
}
