package no.ntnu.rentalroulette.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.CarReviewRepository;
import no.ntnu.rentalroulette.repository.FeatureRepository;
import no.ntnu.rentalroulette.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//TODO: Use services instead of making the rest controller do everything.
@RestController
public class CarController {

  @Autowired
  private CarService carService;

  @Autowired
  private ControllerUtil controllerUtil;

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars(HttpServletRequest request) {
    if (controllerUtil.checkIfAdmin(request)) {
      List<Car> cars = carService.getAllCars();
      return new ResponseEntity<>(cars, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Car> getCar(@PathVariable(value = "id") int id) {
    Car car = carService.getCarById(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  @PostMapping("/cars")
  public ResponseEntity<List<Car>> getCarsByDates(@RequestBody DateRange dateRange) {
    List<Car> cars = carService.getAllCarsByDate(
        dateRange.getDateFrom(),
        dateRange.getDateTo(),
        dateRange.getTimeFrom(),
        dateRange.getTimeTo()
    );
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/provider")
  public ResponseEntity<List<Car>> getCarsByProvider(HttpServletRequest request) {
    List<Car> cars =
        carService.getAllCarsByProviderId(controllerUtil.getUserBasedOnJWT(request).getId());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @PostMapping("cars/add")
  public ResponseEntity<String> addCar(HttpServletRequest request) {
    carService.addCar(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<String> updateCar(HttpServletRequest request, @PathVariable int id) {
    try {
      carService.updateCar(request, id);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalAccessException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<String> deleteCar(HttpServletRequest request, @PathVariable int id) {
    try {
      carService.deleteCar(request, id);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (IllegalAccessException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
