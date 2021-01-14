package microservice.car_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.car_service.controller.response.CarResponse;
import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import microservice.car_service.service.CarService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class CarController {

    private final CarService carService;

    @PostMapping("/cars")
    public String addCar(@RequestBody Car car) {
        carService.saveCar(car);
        return "car saved";
    }

    @GetMapping("/cars")
    public List<CarResponse> getCarsByBrand(@RequestParam(required = false) Brand brand) {
        if (brand == null) {
            return carService.getAllCars();
        }

//        Brand brandEnum = Brand.get(brand);
//        if (brandEnum == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand name does not exist in application.");
//        }

        return carService.getCarsByBrand(brand);
    }
}
