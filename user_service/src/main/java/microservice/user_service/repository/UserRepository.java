package microservice.user_service.repository;

import microservice.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/***
 * Repository for Car object.
 * It's for a MongoDB database.
 */
public interface UserRepository extends MongoRepository<User, String> {
    /***
     * Fínd a User by its username from the database
     * @param username to search for
     * @return an Optional User
     */
    Optional<User> findByUsername(String username);
}
