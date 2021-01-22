package microservice.car_service.service;

import lombok.RequiredArgsConstructor;
import microservice.car_service.controller.response.CarResponse;
import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import microservice.car_service.repository.CarRepository;
import microservice.car_service.shared.DateSpan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/***
 * Service class for all the Car logic
 */
@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final BookingClient bookingClient;

    /***
     * Gets all the Car and their booked dates
     * @return list of CarResponses
     */
    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return fetchBookingsForCars(cars);
    }

    /***
     * Calls the BookingClient to make a request and map the result (if any) into the CarResponse class
     * @param cars the cars that needs to be mapped with their booking dates
     * @return list of CarResponses with their booked dates
     */
    private List<CarResponse> fetchBookingsForCars(List<Car> cars) {
        List<CarResponse> carResponses = mapToCarResponse(cars);

        Map<String, List<DateSpan>> bookingsForCars = bookingClient.getBookedDatesForCars();
        if (bookingsForCars != null) {
            return carResponses.stream()
                    .peek(car -> {
                        if (bookingsForCars.get(car.getCarId()) != null) {
                            car.setBookedDates(bookingsForCars.get(car.getCarId()));
                        }
                    })
                    .collect(Collectors.toList());
        }
        return carResponses;
    }

    /***
     * Persists a Car to Database
     * @param car the car to save
     */
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    /***
     * Gets all the Car and their booked dates by a specific Brand
     * @param brand the brand you want the cars to have
     * @return list of CarResponses
     */
    public List<CarResponse> getCarsByBrand(Brand brand) {
        List<Car> cars = carRepository.findAllByBrand(brand);
        return fetchBookingsForCars(cars);
    }

    /***
     * Mapping a list of Cars into a list of CarResponses
     * @param cars list of the cars needing to be mapped to CarResponse object
     * @return list of CarResponses
     */
    private List<CarResponse> mapToCarResponse(List<Car> cars) {
        return cars.stream()
                .map(CarResponse::new)
                .collect(Collectors.toList());
    }
}
