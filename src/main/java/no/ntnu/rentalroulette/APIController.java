package no.ntnu.rentalroulette;

import java.util.ArrayList;
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

    @GetMapping("/cars")
    public ArrayList<Car> getCars() {
        SessionUtil su = new SessionUtil();
        return new ArrayList<>(su.getAll(Car.class));
    }
}
