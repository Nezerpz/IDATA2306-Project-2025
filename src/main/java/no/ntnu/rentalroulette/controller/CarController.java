package no.ntnu.rentalroulette.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;
import no.ntnu.rentalroulette.repository.UserRepository;
import no.ntnu.rentalroulette.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {


  @Autowired
  private CarRepository carRepository;

  @Autowired
  private ControllerUtil controllerUtil;

  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars() {
    List<Car> cars = new CopyOnWriteArrayList<>(carRepository.findAll());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/{id}")
  public ResponseEntity<Car> getCar(@PathVariable(value = "id") int id) {
    Car car = carRepository.findById(id);
    return new ResponseEntity<>(car, HttpStatus.OK);
  }

  @PostMapping("/cars")
  public ResponseEntity<List<Car>> getCarsByDates(@RequestBody DateRange dateRange) {
    List<Car> cars = new CopyOnWriteArrayList<>(carRepository.findAvailableCars(
        dateRange.getDateFrom(),
        dateRange.getDateTo(),
        dateRange.getTimeFrom(),
        dateRange.getTimeTo()
    ));
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  @GetMapping("/cars/provider")
  public ResponseEntity<List<Car>> getCarsByProvider(HttpServletRequest request) {
    List<Car> cars = new CopyOnWriteArrayList<>(
        carRepository.findAllByProviderId(controllerUtil.getUserBasedOnJWT(request).getId()));
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }

  //TODO: Fix the put, problem in requestbody. Json from frontend is fine.
  /*
  @PutMapping("/cars/{id}")
  public ResponseEntity<String> updateCar(HttpServletRequest request,
                                          @RequestBody Car car) {
    System.out.println("Request Body: " + car.toString());
    Car carCheck = carRepository.findById(car.getId());
    User user = handleJwtAndReturnUser(request);
    if (carCheck.getUser().getId() != user.getId()) {
      return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
    carRepository.updateCarById(car.getId(), car);
    return new ResponseEntity<>(HttpStatus.OK);
  }
*/

  @PutMapping("/cars/{id}")
  public ResponseEntity<String> updateCar(HttpServletRequest request) {

    ObjectNode requestBody = controllerUtil.getRequestBody(request);
    System.out.println("Request Body: " + requestBody.toString());
    int id = requestBody.get("id").asInt();
    System.out.println("ID: " + id);

    return new ResponseEntity<>(HttpStatus.OK);
  }

/*
  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/cars/models")
  public ResponseEntity<List<String>> getCarModels() {
    List<String> cars = new CopyOnWriteArrayList<String>(carRepository.findDistinctCarModel());
    return new ResponseEntity<>(cars, HttpStatus.OK);
  }
 */
}
