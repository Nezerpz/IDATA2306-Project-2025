package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.Car;
import no.ntnu.rentalroulette.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple example controller
 */
@RestController
public class APIController {

  @GetMapping("/hello")
  public String greeting() {
    return "Sjalabais";
  }
}
