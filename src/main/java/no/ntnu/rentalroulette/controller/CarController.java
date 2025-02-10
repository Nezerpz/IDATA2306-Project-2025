package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

  @Autowired
  private CarRepository carRepository;

  @CrossOrigin(origins = "http://localhost:5173")
  @GetMapping("/cars")
  public List<Car> getCars() {
    return carRepository.findAll();
  }
}
