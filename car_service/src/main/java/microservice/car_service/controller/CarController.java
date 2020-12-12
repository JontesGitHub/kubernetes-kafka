package microservice.car_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.car_service.controller.response.CarResponse;
import microservice.car_service.model.Car;
import microservice.car_service.service.CarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
