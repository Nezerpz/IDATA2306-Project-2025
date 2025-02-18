package no.ntnu.rentalroulette.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

  @Autowired
  private CarRepository carRepository;

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/cars")
  public ResponseEntity<List<Car>> getCars() {
    List<Car> cars = new CopyOnWriteArrayList<>(carRepository.findAll());
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
