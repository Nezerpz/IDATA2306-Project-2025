package no.ntnu.rentalroulette;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple example controller
 */
@RestController
public class APIController {
    @GetMapping("/hello")
    public String greeting() {
        return "Hello, World";
    }

}
