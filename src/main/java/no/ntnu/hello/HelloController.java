package no.ntnu.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple example controller
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String greeting() {
        return "Hello, World";
    }
}
