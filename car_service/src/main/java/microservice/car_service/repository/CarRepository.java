package microservice.car_service.repository;

import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * Repository for Car object.
 * It's for a MongoDB database.
 */
@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    /***
     * Fetches all cars from database by a specific brand.
     * @param brand what type of Brand you search for
     * @return a list of all cars with the given brand
     */
    List<Car> findAllByBrand(Brand brand);
}
