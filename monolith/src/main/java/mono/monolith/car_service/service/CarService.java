package mono.monolith.car_service.service;

import lombok.RequiredArgsConstructor;
import mono.monolith.car_service.controller.response.CarResponse;
import mono.monolith.car_service.model.Car;
import mono.monolith.car_service.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    public List<CarResponse> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarResponse::new)
                .collect(Collectors.toList());
    }

    public void saveCar(Car car) {
        carRepository.save(car);
    }
}
