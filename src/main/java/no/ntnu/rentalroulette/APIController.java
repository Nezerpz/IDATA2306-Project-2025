package no.ntnu.rentalroulette;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Simple example controller
 */
@RestController
public class APIController {

    @Autowired
    private SessionUtil sessionUtil;

    @GetMapping("/hello")
    @Operation(
        summary = "A simple test endpoint",
        description = "Returns a small text"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "The small text returned in response body"
        )
    })
    public String greeting() {
        return "Sjalabais";
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/cars")
    public List<Car> getCars() {
        return sessionUtil.getAll(Car.class);
    }
}
