package no.ntnu.rentalroulette.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

  //TODO: Find a better controller for enum endpoints endpoint


}
