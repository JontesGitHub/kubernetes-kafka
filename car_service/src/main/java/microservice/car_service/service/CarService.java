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

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;
    private final BookingClient bookingClient;

    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return fetchBookingsForCars(cars);
    }

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

    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public List<CarResponse> getCarsByBrand(Brand brand) {
        List<Car> cars = carRepository.findAllByBrand(brand);
        return fetchBookingsForCars(cars);
    }

    private List<CarResponse> mapToCarResponse(List<Car> cars) {
        return cars.stream()
                .map(CarResponse::new)
                .peek(car -> car.setBookedDates(getBookingDates(car.getCarId())))
                .collect(Collectors.toList());
    }

    private List<DateSpan> getBookingDates(String carId) {
        // TODO: anrop till booking för att hämta en bils datum
        return null;
    }
}
