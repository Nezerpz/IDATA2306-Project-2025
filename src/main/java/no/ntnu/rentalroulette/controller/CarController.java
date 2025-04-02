package no.ntnu.rentalroulette.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController extends ControllerUtil {


  @Autowired
  private CarRepository carRepository;

  @Autowired
  public CarController(JwtUtil jwtUtil, UserRepository userRepository) {
    super(jwtUtil, userRepository);
  }

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
        carRepository.findAllByProviderId(handleJwtAndReturnUser(request).getId()));
    return new ResponseEntity<>(cars, HttpStatus.OK);
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
