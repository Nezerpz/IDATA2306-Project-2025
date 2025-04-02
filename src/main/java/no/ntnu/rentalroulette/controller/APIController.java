package no.ntnu.rentalroulette.controller;

import java.util.List;
import no.ntnu.rentalroulette.entity.Order;
import no.ntnu.rentalroulette.enums.Manufacturer;
import no.ntnu.rentalroulette.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Arrays;

/**
 * Simple example controller
 */
@RestController
public class APIController {

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

  //TODO: Find a better controller for this endpoint
  @GetMapping("/manufacturers")
  public ResponseEntity<List<Manufacturer>> getManufacturers() {
    return ResponseEntity.ok(List.of(Manufacturer.values()));
  }

}
