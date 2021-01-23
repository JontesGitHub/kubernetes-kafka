package microservice.payment_service.repository;

import microservice.payment_service.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/***
 * Repository for Car object.
 * It's for a MongoDB database.
 */
@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
}
