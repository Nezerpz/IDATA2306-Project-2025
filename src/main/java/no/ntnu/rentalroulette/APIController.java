package no.ntnu.rentalroulette;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple example controller
 */
@RestController
public class APIController {

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("/hello")
    public String greeting() {
        return "Sjalabais";
    }

    @GetMapping("/cars")
    public ArrayList<Car> getCars() {
        return new ArrayList<>(sessionUtil.getAll(Car.class));
    }
}
