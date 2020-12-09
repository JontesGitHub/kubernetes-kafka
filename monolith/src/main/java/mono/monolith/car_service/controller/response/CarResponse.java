package mono.monolith.car_service.controller.response;

import lombok.*;
import mono.monolith.car_service.model.Car;
import mono.monolith.shared.DateSpan;

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
