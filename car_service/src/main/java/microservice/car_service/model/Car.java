package microservice.car_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Entity of a Car.
 * This is what gets persisted to the Database.
 * MongoDB collection = Car
 */
@Data
@NoArgsConstructor
public class Car {
    private String id;
    private Brand brand;
    private String model;
}
