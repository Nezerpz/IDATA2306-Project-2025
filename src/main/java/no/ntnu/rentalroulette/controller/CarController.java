package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<List<Car>> getCarsByProvider(HttpServletRequest request) {
    List<Car> cars =
        carService.getAllCarsByProviderId(controllerUtil.getUserBasedOnJWT(request).getId());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @PostMapping("cars/add")
  @PreAuthorize("hasRole('PROVIDER')")
  public ResponseEntity<String> addCar(HttpServletRequest request) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    carService.addCar(requestBody,
        controllerUtil.getFeaturesFromRequestBody(requestBody.get("features")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/cars/{id}")
  @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
  public ResponseEntity<String> updateCar(HttpServletRequest request, @PathVariable int id) {
    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    carService.updateCar(requestBody, id,
        controllerUtil.getFeaturesFromRequestBody(requestBody.get("features")));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/cars/{id}")
  @PreAuthorize("hasRole('PROVIDER') or hasRole('ADMIN')")
  public ResponseEntity<String> deleteCar(@PathVariable int id) {
    carService.deleteCar(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
