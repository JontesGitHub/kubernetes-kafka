package mono.monolith.car_service.controller;

import lombok.RequiredArgsConstructor;
import mono.monolith.car_service.controller.response.CarResponse;
import mono.monolith.car_service.model.Car;
import mono.monolith.car_service.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CarController {

    private final CarService carService;


    @GetMapping("/cars")
    public List<CarResponse> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/cars")
    public String addCar(@RequestBody Car car) {
        carService.saveCar(car);
        return "car saved";
    }
}
