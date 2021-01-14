package microservice.car_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Car {
    private String id;
    private Brand brand;
    private String model;
}
