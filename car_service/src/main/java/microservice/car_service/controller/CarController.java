package microservice.car_service.controller;

import lombok.RequiredArgsConstructor;
import microservice.car_service.controller.response.CarResponse;
import microservice.car_service.model.Brand;
import microservice.car_service.model.Car;
import microservice.car_service.service.CarService;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/***
 * Controller for Car-Microservice containing endpoints
 */
@RequiredArgsConstructor
@RestController
public class CarController {

    private final CarService carService;

    /***
     * Endpoint to Save a new Car to Database
     * @param car the body from the request
     * @return "car saved" status code 200
     */
    @PostMapping("/cars")
    public ResponseEntity<String> addCar(@RequestBody Car car) {
        carService.saveCar(car);
        return ResponseEntity.ok("car saved");
    }

    /***
     * Endpoint to GET all cars or GET all cars by a brand
     * @param brand optional param, for searching for a specific brand
     * @return list of CarResponses
     */
    @GetMapping("/cars")
    public ResponseEntity<List<CarResponse>> getCarsByBrand(@RequestParam(required = false) Brand brand) {
        if (brand == null) {
            return ResponseEntity.ok(carService.getAllCars());
        }

        return ResponseEntity.ok(carService.getCarsByBrand(brand));
    }
}
