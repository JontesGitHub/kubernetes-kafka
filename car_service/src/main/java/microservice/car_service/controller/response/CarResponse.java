package microservice.car_service.controller.response;

import lombok.*;
import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import microservice.car_service.shared.DateSpan;

import java.util.List;

/***
 * Object to send as a response
 * It's made up of data from Car.java and the dateSpan from Booking.java
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Setter
public class CarResponse {
    private final String carId;
    private final String model;
    private final Brand brand;
    private List<DateSpan> bookedDates;

    /***
     * Creating a CarResponse with data from a Car object
     * @param car Car object
     */
    public CarResponse(Car car) {
        this.carId = car.getId();
        this.model = car.getModel();
        this.brand = car.getBrand();
    }
}
