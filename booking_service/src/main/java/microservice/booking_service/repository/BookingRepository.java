package microservice.booking_service.repository;

import microservice.booking_service.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * Repository for Booking object.
 * It's for a MongoDB database.
 */
@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    /***
     * Fetches all cars from database by a userId
     * @param userId what userId you search for.
     * @return a list of bookings
     */
    List<Booking> findAllByUserId(String userId);
}
