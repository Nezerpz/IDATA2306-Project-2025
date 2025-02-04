package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/cars")
    public List<Car> getCars() {
        return sessionUtil.getAll(Car.class);
    }
}
