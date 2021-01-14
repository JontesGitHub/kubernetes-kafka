package microservice.car_service.repository;

import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
    List<Car> findAllByBrand(Brand brand);
}
