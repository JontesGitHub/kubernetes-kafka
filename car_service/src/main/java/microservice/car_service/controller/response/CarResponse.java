package microservice.car_service.controller.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import microservice.car_service.model.Car;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CarResponse {
    private final String carId;
    private final String model;
//    private final DateSpan dateSpan;

    public CarResponse(Car car) {
        this.carId = car.getId();
        this.model = car.getModel();
    }
}
